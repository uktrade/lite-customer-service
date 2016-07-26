package uk.gov.bis.lite.sar.client.unmarshall;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.exception.SiteNotFoundException;
import uk.gov.bis.lite.sar.model.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class SiteServiceUnmarshaller extends ServiceUnmarshaller {
  private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceUnmarshaller.class);
  private static final String SAR_REF = "SAR_REF";
  private static final String SITE_REF = "SITE_REF";
  private static final String COMPANY_NAME = "COMPANY_NAME";
  private static final String APPLICANT_TYPE = "APPLICANT_TYPE";
  private static final String ADDRESS = "ADDRESS";
  private static final String DIVISION = "DIVISION";
  private static final String OCCUPANCY_STATUS = "OCCUPANCY_STATUS";
  private static final String ERROR = "ERROR";

  public List<Site> execute(SOAPMessage message, String companyName) {
    return (List<Site>) super.execute(message, companyName, "//COMPANY_SITE_LIST");
  }

  protected List<Site> parseSoapBody(NodeList nodeList, XPath xpath, String companyName) {
    List<Site> foundSitesList = new ArrayList<>();
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      nodeList = nodeList.item(0).getChildNodes();
      Node errorNode = (Node) xpath.evaluate(ERROR, nodeList, XPathConstants.NODE);
      if (errorNode != null) {
        if (errorNode.getTextContent().contains(" not found")) {
          throw new SiteNotFoundException(companyName);
        } else {
          throw new RuntimeException("Unexpected Error Occurred: " + errorNode.getTextContent());
        }
      }
      for (int i = 0; i < nodeList.getLength(); i++) {
        Site site = new Site();
        Node singleSiteNode = nodeList.item(i).cloneNode(true);
        if (singleSiteNode.getNodeType() == Node.ELEMENT_NODE) {
          Node siteRef = (Node) xpath.evaluate(SITE_REF, singleSiteNode, XPathConstants.NODE);
          if (siteRef != null) {
            site.setSiteRef(siteRef.getTextContent());
          }
          Node sarRef = (Node) xpath.evaluate(SAR_REF, singleSiteNode, XPathConstants.NODE);
          if (sarRef != null) {
            site.setSarRef(sarRef.getTextContent());
          }
          Node nameNode = (Node) xpath.evaluate(COMPANY_NAME, singleSiteNode, XPathConstants.NODE);
          if (nameNode != null) {
            site.setCompanyName(nameNode.getTextContent());
          }
          Node applicantTypeNode = (Node) xpath.evaluate(APPLICANT_TYPE, singleSiteNode, XPathConstants.NODE);
          if (applicantTypeNode != null) {
            site.setApplicantType(applicantTypeNode.getTextContent());
          }
          Node addressNode = (Node) xpath.evaluate(ADDRESS, singleSiteNode, XPathConstants.NODE);
          if (addressNode != null) {
            site.setAddress(addressNode.getTextContent());
          }
          Node divisionNode = (Node) xpath.evaluate(DIVISION, singleSiteNode, XPathConstants.NODE);
          if (divisionNode != null) {
            site.setDivision(divisionNode.getTextContent());
          }
          Node occupancyNode = (Node) xpath.evaluate(OCCUPANCY_STATUS, singleSiteNode, XPathConstants.NODE);
          if (occupancyNode != null) {
            site.setOccupancyStatus(occupancyNode.getTextContent());
          }
          foundSitesList.add(site);
        }
      }
    } catch (XPathExpressionException e) {
      throw new RuntimeException("An error occurred while parsing the SOAP response body", e);
    }
    stopwatch.stop();
    LOGGER.info("The unmarshalling of the Spire Response took " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds ");
    return foundSitesList;
  }
}
