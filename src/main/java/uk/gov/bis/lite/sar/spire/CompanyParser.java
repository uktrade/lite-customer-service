package uk.gov.bis.lite.sar.spire;


import static uk.gov.bis.lite.spire.client.SpireName.APPLICANT_TYPE;
import static uk.gov.bis.lite.spire.client.SpireName.COMPANY_NUMBER;
import static uk.gov.bis.lite.spire.client.SpireName.COUNTRY_OF_ORIGIN;
import static uk.gov.bis.lite.spire.client.SpireName.NAME;
import static uk.gov.bis.lite.spire.client.SpireName.NATURE_OF_BUSINESS;
import static uk.gov.bis.lite.spire.client.SpireName.ORGANISATION_TYPE;
import static uk.gov.bis.lite.spire.client.SpireName.REGISTERED_ADDRESS;
import static uk.gov.bis.lite.spire.client.SpireName.REGISTRATION_STATUS;
import static uk.gov.bis.lite.spire.client.SpireName.SAR_REF;
import static uk.gov.bis.lite.spire.client.SpireName.SHORT_NAME;
import static uk.gov.bis.lite.spire.client.SpireName.WEBSITE_LIST;
import static uk.gov.bis.lite.spire.client.SpireName.WEBSITE_URL;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.spire.client.model.SpireCompany;
import uk.gov.bis.lite.spire.client.model.SpireOrganisationType;
import uk.gov.bis.lite.spire.client.model.SpireResponse;
import uk.gov.bis.lite.spire.client.model.SpireWebsite;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class CompanyParser extends SpireParser<List<SpireCompany>> {

  public List<SpireCompany> getResult(SpireResponse spireResponse) {
    return parseCompanies(getNodeList(spireResponse.getMessage(), "//COMPANIES_LIST"));
  }

  private List<SpireCompany> parseCompanies(NodeList nodeList) {
    List<SpireCompany> companies = new ArrayList<>();
    XPath xpath = XPathFactory.newInstance().newXPath();
    NodeList nodes = nodeList.item(0).getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      SpireCompany company = new SpireCompany();
      Node node = nodes.item(i).cloneNode(true);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        getValue(node, xpath, SAR_REF).ifPresent(company::setSarRef);
        getValue(node, xpath, NAME).ifPresent(company::setName);
        getValue(node, xpath, SHORT_NAME).ifPresent(company::setShortName);
        getValue(node, xpath, ORGANISATION_TYPE).ifPresent(v -> company.setSpireOrganisationType(SpireOrganisationType.valueOf(v)));
        getValue(node, xpath, COMPANY_NUMBER).ifPresent(company::setNumber);
        getValue(node, xpath, REGISTRATION_STATUS).ifPresent(company::setRegistrationStatus);
        getValue(node, xpath, REGISTERED_ADDRESS).ifPresent(company::setRegisteredAddress);
        getValue(node, xpath, APPLICANT_TYPE).ifPresent(company::setApplicantType);
        getValue(node, xpath, NATURE_OF_BUSINESS).ifPresent(company::setNatureOfBusiness);
        getValue(node, xpath, COUNTRY_OF_ORIGIN).ifPresent(company::setCountryOfOrigin);

        List<SpireWebsite> webSites = new ArrayList<>();
        Node websitesNode = null;
        try {
          websitesNode = (Node) xpath.evaluate(WEBSITE_LIST, node, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
          e.printStackTrace();
        }
        if (websitesNode != null) {
          SpireWebsite website = new SpireWebsite();
          NodeList webSitesChildren = websitesNode.getChildNodes();
          for (int k = 0; k < webSitesChildren.getLength(); k++) {
            Node websiteNode = webSitesChildren.item(k).cloneNode(true);
            getValue(websiteNode, xpath, WEBSITE_URL).ifPresent(website::setUrl);
            //getValue(websiteNode, xpath, WEBSITE_ACTION).ifPresent(website::setAction);
            webSites.add(website);
          }
        }
        company.setWebsites(webSites);
        companies.add(company);
      }
    }
    return companies;
  }


}

