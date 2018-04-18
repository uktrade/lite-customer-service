package uk.gov.bis.lite.customer.spire.parsers;

import org.w3c.dom.Node;
import uk.gov.bis.lite.common.spire.client.SpireResponse;
import uk.gov.bis.lite.common.spire.client.parser.SpireParser;
import uk.gov.bis.lite.customer.spire.model.SpireCompany;
import uk.gov.bis.lite.customer.spire.model.SpireOrganisationType;
import uk.gov.bis.lite.customer.spire.model.SpireWebsite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyParser implements SpireParser<List<SpireCompany>> {

  @Override
  public List<SpireCompany> parseResponse(SpireResponse spireResponse) {

    // It is possible to receive error nodes back from Spire, for example:
    // <COMPANIES_LIST>
    //    <ERROR>Company for provided companyNumber not found.</ERROR>
    // </COMPANIES_LIST>
    // So we remove any ERROR node from result

    List<Node> nodes = spireResponse.getElementChildNodesForList("//COMPANIES_LIST")
        .stream().filter(n -> !n.getNodeName().equals("ERROR")).collect(Collectors.toList());
    return getCompaniesFromNodes(nodes);
  }

  private List<SpireCompany> getCompaniesFromNodes(List<Node> nodes) {
    List<SpireCompany> companies = new ArrayList<>();
    for (Node node : nodes) {
      SpireCompany company = new SpireCompany();
      SpireResponse.getNodeValue(node, "SAR_REF").ifPresent(company::setSarRef);
      SpireResponse.getNodeValue(node, "COMPANY_NAME").ifPresent(company::setCompanyName);
      SpireResponse.getNodeValue(node, "SHORT_NAME").ifPresent(company::setShortName);
      SpireResponse.getNodeValue(node, "ORGANISATION_TYPE").ifPresent(v -> company.setSpireOrganisationType(SpireOrganisationType.valueOf(v)));
      SpireResponse.getNodeValue(node, "COMPANY_NUMBER").ifPresent(company::setCompanyNumber);
      SpireResponse.getNodeValue(node, "REGISTRATION_STATUS").ifPresent(company::setRegistrationStatus);
      SpireResponse.getNodeValue(node, "REGISTERED_ADDRESS").ifPresent(company::setRegisteredAddress);
      SpireResponse.getNodeValue(node, "APPLICANT_TYPE").ifPresent(company::setApplicantType);
      SpireResponse.getNodeValue(node, "NATURE_OF_BUSINESS").ifPresent(company::setNatureOfBusiness);
      SpireResponse.getNodeValue(node, "COUNTRY_OF_ORIGIN").ifPresent(company::setCountryOfOrigin);

      List<SpireWebsite> webSites = new ArrayList<>();
      List<Node> websiteNodes = SpireResponse.getChildrenOfChildNode(node, "WEBSITE_LIST");
      for (Node websiteNode : websiteNodes) {
        SpireWebsite website = new SpireWebsite();
        SpireResponse.getNodeValue(websiteNode, "URL").ifPresent(website::setUrl);
        webSites.add(website);
      }
      company.setWebsites(webSites);
      companies.add(company);
    }
    return companies;
  }
}
