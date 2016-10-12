package uk.gov.bis.lite.sar.client.unmarshall;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.model.spire.SpireSite;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class CreateLiteSarUnmarshaller extends ServiceUnmarshaller {
  private static final Logger LOGGER = LoggerFactory.getLogger(CreateLiteSarUnmarshaller.class);
  private static final String SAR_REF = "SAR_REF";
  private static final String ERROR = "ERROR";

  public List<SpireSite> execute(SOAPMessage message) {
    return (List<SpireSite>) super.execute(message, "//RESPONSE");
  }

  protected List<String> parseSoapBody(NodeList nodeList, XPath xpath) {
    List<String> sarRefs = new ArrayList<>();
    try {
      NodeList nodes = nodeList.item(0).getChildNodes();
      Node errorNode = (Node) xpath.evaluate(ERROR, nodes, XPathConstants.NODE);
      if (errorNode != null) {
        throw new RuntimeException("Unexpected Error Occurred: " + errorNode.getTextContent());
      }
      for (int i = 0; i < nodes.getLength(); i++) {
        Node singleSiteNode = nodes.item(i).cloneNode(true);
        if (singleSiteNode.getNodeType() == Node.ELEMENT_NODE) {
          getNodeValue(singleSiteNode, xpath, SAR_REF).ifPresent(sarRefs::add);
        }
      }
    } catch (XPathExpressionException e) {
      throw new RuntimeException("An error occurred while parsing the SOAP response body", e);
    }
    return sarRefs;
  }
}
