package uk.gov.bis.lite.sar.client.unmarshall;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.exception.CompanyNotFoundException;
import uk.gov.bis.lite.sar.model.spire.Company;
import uk.gov.bis.lite.sar.model.spire.OrganisationType;
import uk.gov.bis.lite.sar.model.spire.Website;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class CompanyUnmarshaller extends ServiceUnmarshaller {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyUnmarshaller.class);
  private static final String REFERENCE = "SAR_REF";
  private static final String NAME = "COMPANY_NAME";
  private static final String SHORT_NAME = "SHORT_NAME";
  private static final String ORGANISATION_TYPE = "ORGANISATION_TYPE";
  private static final String COMPANY_NUMBER = "COMPANY_NUMBER";
  private static final String REGISTRATION_STATUS = "REGISTRATION_STATUS";
  private static final String REGISTERED_ADDRESS = "REGISTERED_ADDRESS";
  private static final String APPLICANT_TYPE = "APPLICANT_TYPE";
  private static final String NATURE_OF_BUSINESS = "NATURE_OF_BUSINESS";
  private static final String WEBSITE_LIST = "WEBSITE_LIST";
  private static final String WEBSITE_URL = "URL";
  private static final String WEBSITE_ACTION = "ACTION";
  private static final String COUNTRY_OF_ORIGIN = "COUNTRY_OF_ORIGIN";
  private static final String ERROR = "ERROR";

  public List<Company> execute(SOAPMessage message) {
    return (List<Company>) super.execute(message, "//COMPANIES_LIST");
  }

  protected List<Company> parseSoapBody(NodeList nodeList, XPath xpath) {
    List<Company> foundCompaniesList = new ArrayList<>();
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      nodeList = nodeList.item(0).getChildNodes();
      Node errorNode = (Node) xpath.evaluate(ERROR, nodeList, XPathConstants.NODE);
      if (errorNode != null) {
        if (errorNode.getTextContent().contains("companyName not found")) {
          throw new CompanyNotFoundException("CompanyNotFound");
        } else {
          throw new RuntimeException("Unexpected Error Occurred: " + errorNode.getTextContent());
        }
      }

      for (int i = 0; i < nodeList.getLength(); i++) {
        Company company = new Company();
        Node currentCompanyNode = nodeList.item(i).cloneNode(true);
        if (currentCompanyNode.getNodeType() == Node.ELEMENT_NODE) {

          getNodeValue(currentCompanyNode, xpath, REFERENCE).ifPresent(company::setSarRef);
          getNodeValue(currentCompanyNode, xpath, NAME).ifPresent(company::setName);
          getNodeValue(currentCompanyNode, xpath, SHORT_NAME).ifPresent(company::setShortName);
          getNodeValue(currentCompanyNode, xpath, ORGANISATION_TYPE)
              .ifPresent(v -> company.setOrganisationType(OrganisationType.valueOf(v)));
          getNodeValue(currentCompanyNode, xpath, COMPANY_NUMBER).ifPresent(company::setNumber);
          getNodeValue(currentCompanyNode, xpath, REGISTRATION_STATUS).ifPresent(company::setRegistrationStatus);
          getNodeValue(currentCompanyNode, xpath, REGISTERED_ADDRESS).ifPresent(company::setRegisteredAddress);
          getNodeValue(currentCompanyNode, xpath, APPLICANT_TYPE).ifPresent(company::setApplicantType);
          getNodeValue(currentCompanyNode, xpath, NATURE_OF_BUSINESS).ifPresent(company::setNatureOfBusiness);
          getNodeValue(currentCompanyNode, xpath, COUNTRY_OF_ORIGIN).ifPresent(company::setCountryOfOrigin);

          List<Website> webSiteList = new ArrayList<>();
          Node websitesNode = (Node) xpath.evaluate(WEBSITE_LIST, currentCompanyNode, XPathConstants.NODE);
          if (websitesNode != null) {
            Website companySite = new Website();
            NodeList webSitesChildren = websitesNode.getChildNodes();
            for (int k = 1; k < webSitesChildren.getLength(); k = k + 2) {
              Node companyWebsiteNode = webSitesChildren.item(k).cloneNode(true);
              getNodeValue(companyWebsiteNode, xpath, WEBSITE_URL).ifPresent(companySite::setUrl);
              getNodeValue(companyWebsiteNode, xpath, WEBSITE_ACTION).ifPresent(companySite::setAction);
              webSiteList.add(companySite);
            }
          }
          company.setWebsites(webSiteList);
          foundCompaniesList.add(company);
        }
      }
    } catch (XPathExpressionException e) {
      throw new RuntimeException("An error occurred while parsing the SOAP response body", e);
    }
    stopwatch.stop();
    LOGGER.info("The unmarshalling of the Spire Response took " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds ");
    return foundCompaniesList;
  }
}
