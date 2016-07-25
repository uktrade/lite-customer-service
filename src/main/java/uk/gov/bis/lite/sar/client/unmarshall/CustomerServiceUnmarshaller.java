package uk.gov.bis.lite.sar.client.unmarshall;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.model.Company;
import uk.gov.bis.lite.sar.model.OrganisationType;
import uk.gov.bis.lite.sar.model.Website;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class CustomerServiceUnmarshaller {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceUnmarshaller.class);
  private static final String REFERENCE = "SAR_REF";
  private static final String NAME = "COMPANY_NAME";
  private static final String SHORT_NAME = "SHORT_NAME";
  private static final String ORGANISATION_TYPE = "ORGANISATION_TYPE";
  private static final String COMPANY_NUMBER = "COMPANY_NUMBER";
  private static final String REGISTRATION_STATUS = "REGISTRATION_STATUS";
  private static final String APPLICANT_TYPE = "APPLICANT_TYPE";
  private static final String NATURE_OF_BUSINESS = "NATURE_OF_BUSINESS";
  private static final String WEBSITE_LIST = "WEBSITE_LIST";
  private static final String WEBSITE_URL = "URL";
  private static final String WEBSITE_ACTION = "ACTION";
  private static final String COUNTRY_OF_ORIGIN = "COUNTRY_OF_ORIGIN";

  public List<Company> execute(SOAPMessage message) {

    final SOAPBody soapBody;
    NodeList nodeList;
    try {
      soapBody = message.getSOAPBody();
      XPath xpath = XPathFactory.newInstance().newXPath();
      nodeList = (NodeList) xpath.evaluate("//COMPANIES_LIST", soapBody, XPathConstants.NODESET);
      if (nodeList != null) {
        return parseSoapBody(nodeList, xpath);
      }
      return null;
    } catch (SOAPException | XPathExpressionException e) {
      throw new RuntimeException("An error occurred while extracting the SOAP Response Body", e);
    }
  }

  public List<Company> parseSoapBody(NodeList nodeList, XPath xpath) {
    List<Company> foundCompaniesList = new ArrayList<>();
    nodeList = nodeList.item(0).getChildNodes();
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Company company = new Company();
      Node currentCompanyNode = nodeList.item(i).cloneNode(true);
      if (currentCompanyNode.getNodeType() == Node.ELEMENT_NODE) {
        try {
          company.setSarRef(((Node) xpath.evaluate(REFERENCE, currentCompanyNode, XPathConstants.NODE)).getTextContent());

          Node nameNode = (Node) xpath.evaluate(NAME, currentCompanyNode, XPathConstants.NODE);
          if (nameNode != null) {
            company.setShortName(nameNode.getTextContent());
          }
          Node shortName = (Node) xpath.evaluate(SHORT_NAME, currentCompanyNode, XPathConstants.NODE);
          if (shortName != null) {
            company.setShortName(shortName.getTextContent());
          }
          Node organisationTypeNode = (Node) xpath.evaluate(ORGANISATION_TYPE, currentCompanyNode, XPathConstants.NODE);
          if (organisationTypeNode != null) {
            company.setOrganisationType(OrganisationType.valueOf(organisationTypeNode.getTextContent()));
          }
          Node companyNumberNode = (Node) xpath.evaluate(COMPANY_NUMBER, currentCompanyNode, XPathConstants.NODE);
          if (companyNumberNode != null) {
            company.setNumber(companyNumberNode.getTextContent());
          }
          Node registrationStatusNode = (Node) xpath.evaluate(REGISTRATION_STATUS, currentCompanyNode, XPathConstants.NODE);
          if (registrationStatusNode != null) {
            company.setRegistrationStatus(registrationStatusNode.getTextContent());
          }
          Node applicantTypeNode = (Node) xpath.evaluate(APPLICANT_TYPE, currentCompanyNode, XPathConstants.NODE);
          if (applicantTypeNode != null) {
            company.setApplicantType(applicantTypeNode.getTextContent());
          }
          Node natureOfBusinessNode = (Node) xpath.evaluate(NATURE_OF_BUSINESS, currentCompanyNode, XPathConstants.NODE);
          if (natureOfBusinessNode != null) {
            company.setNatureOfBusiness(natureOfBusinessNode.getTextContent());
          }
          Node countryNode = (Node) xpath.evaluate(COUNTRY_OF_ORIGIN, currentCompanyNode, XPathConstants.NODE);
          if (countryNode != null) {
            company.setCountryOfOrigin(countryNode.getTextContent());
          }
          List<Website> webSiteList = new ArrayList<>();
          Node websitesNode = (Node) xpath.evaluate(WEBSITE_LIST, currentCompanyNode, XPathConstants.NODE);
          if (websitesNode != null) {
            Website companySite = new Website();
            NodeList webSitesChildren = websitesNode.getChildNodes();
            for (int k = 1; k < webSitesChildren.getLength(); k = k + 2) {
              Node companyWebsiteNode = webSitesChildren.item(k).cloneNode(true);
              Node websiteUrlNode = (Node) xpath.evaluate(WEBSITE_URL, companyWebsiteNode, XPathConstants.NODE);
              if (websiteUrlNode != null) {
                companySite.setUrl(websiteUrlNode.getTextContent());
              }
              Node websiteActionNode = (Node) xpath.evaluate(WEBSITE_ACTION, companyWebsiteNode, XPathConstants.NODE);
              if (websiteActionNode != null) {
                companySite.setAction(websiteActionNode.getTextContent());
              }
              webSiteList.add(companySite);
            }
          }
          company.setWebsites(webSiteList);
          foundCompaniesList.add(company);
        } catch (XPathExpressionException e) {
          throw new RuntimeException("An error occurred while parsing the SOAP response body", e);
        }
      }
    }
    stopwatch.stop();
    LOGGER.info("The unmarshalling of the Spire Response took " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds ");
    return foundCompaniesList;
  }
}
