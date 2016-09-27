package uk.gov.bis.lite.sar.client.unmarshall;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.exception.SiteNotFoundException;
import uk.gov.bis.lite.sar.model.spire.SpireSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class SiteUnmarshaller extends ServiceUnmarshaller {
  private static final Logger LOGGER = LoggerFactory.getLogger(SiteUnmarshaller.class);
  private static final String SAR_REF = "SAR_REF";
  private static final String SITE_REF = "SITE_REF";
  private static final String COMPANY_NAME = "COMPANY_NAME";
  private static final String APPLICANT_TYPE = "APPLICANT_TYPE";
  private static final String ADDRESS = "ADDRESS";
  private static final String DIVISION = "DIVISION";
  private static final String OCCUPANCY_STATUS = "OCCUPANCY_STATUS";
  private static final String ERROR = "ERROR";

  public List<SpireSite> execute(SOAPMessage message) {
    return (List<SpireSite>) super.execute(message, "//COMPANY_SITE_LIST");
  }

  protected List<SpireSite> parseSoapBody(NodeList nodeList, XPath xpath) {
    List<SpireSite> foundSitesList = new ArrayList<>();
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      nodeList = nodeList.item(0).getChildNodes();
      Node errorNode = (Node) xpath.evaluate(ERROR, nodeList, XPathConstants.NODE);
      if (errorNode != null) {
        if (errorNode.getTextContent().contains(" not found")) {
          throw new SiteNotFoundException("SiteNotFound");
        } else {
          throw new RuntimeException("Unexpected Error Occurred: " + errorNode.getTextContent());
        }
      }
      for (int i = 0; i < nodeList.getLength(); i++) {
        SpireSite site = new SpireSite();
        Node singleSiteNode = nodeList.item(i).cloneNode(true);
        if (singleSiteNode.getNodeType() == Node.ELEMENT_NODE) {
          getNodeValue(singleSiteNode, xpath, SITE_REF).ifPresent(site::setSiteRef);
          getNodeValue(singleSiteNode, xpath, SAR_REF).ifPresent(site::setSarRef);
          getNodeValue(singleSiteNode, xpath, COMPANY_NAME).ifPresent(site::setCompanyName);
          getNodeValue(singleSiteNode, xpath, APPLICANT_TYPE).ifPresent(site::setApplicantType);
          getNodeValue(singleSiteNode, xpath, ADDRESS).ifPresent(site::setAddress);
          getNodeValue(singleSiteNode, xpath, DIVISION).ifPresent(site::setDivision);
          getNodeValue(singleSiteNode, xpath, OCCUPANCY_STATUS).ifPresent(site::setOccupancyStatus);
          foundSitesList.add(site);
        }
      }
    } catch (XPathExpressionException e) {
      throw new RuntimeException("An error occurred while parsing the SOAP response body", e);
    }
    stopwatch.stop();
    LOGGER.info("Unmarshalling of Spire Response took " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds ");
    return foundSitesList;
  }
}
