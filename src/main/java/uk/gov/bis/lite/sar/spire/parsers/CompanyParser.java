package uk.gov.bis.lite.sar.spire.parsers;


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
import uk.gov.bis.lite.sar.spire.model.SpireCompany;
import uk.gov.bis.lite.sar.spire.model.SpireOrganisationType;
import uk.gov.bis.lite.sar.spire.model.SpireWebsite;
import uk.gov.bis.lite.spire.client.SpireResponse;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.ArrayList;
import java.util.List;

public class CompanyParser implements SpireParser<List<SpireCompany>> {

  @Override
  public List<SpireCompany> parseResponse(SpireResponse spireResponse) {
    return getCompaniesFromNodes(spireResponse.getElementChildNodesForList("//COMPANIES_LIST"));
  }

  private List<SpireCompany> getCompaniesFromNodes(List<Node> nodes) {
    List<SpireCompany> companies = new ArrayList<>();
    for (Node node : nodes) {
      SpireCompany company = new SpireCompany();
      SpireResponse.getNodeValue(node, SAR_REF).ifPresent(company::setSarRef);
      SpireResponse.getNodeValue(node, NAME).ifPresent(company::setName);
      SpireResponse.getNodeValue(node, SHORT_NAME).ifPresent(company::setShortName);
      SpireResponse.getNodeValue(node, ORGANISATION_TYPE).ifPresent(v -> company.setSpireOrganisationType(SpireOrganisationType.valueOf(v)));
      SpireResponse.getNodeValue(node, COMPANY_NUMBER).ifPresent(company::setNumber);
      SpireResponse.getNodeValue(node, REGISTRATION_STATUS).ifPresent(company::setRegistrationStatus);
      SpireResponse.getNodeValue(node, REGISTERED_ADDRESS).ifPresent(company::setRegisteredAddress);
      SpireResponse.getNodeValue(node, APPLICANT_TYPE).ifPresent(company::setApplicantType);
      SpireResponse.getNodeValue(node, NATURE_OF_BUSINESS).ifPresent(company::setNatureOfBusiness);
      SpireResponse.getNodeValue(node, COUNTRY_OF_ORIGIN).ifPresent(company::setCountryOfOrigin);

      List<SpireWebsite> webSites = new ArrayList<>();
      List<Node> websiteNodes = SpireResponse.getChildrenOfChildNode(node, WEBSITE_LIST);
      for (Node websiteNode : websiteNodes) {
        SpireWebsite website = new SpireWebsite();
        SpireResponse.getNodeValue(websiteNode, WEBSITE_URL).ifPresent(website::setUrl);
        webSites.add(website);
      }
      company.setWebsites(webSites);
      companies.add(company);
    }
    return companies;
  }
}
