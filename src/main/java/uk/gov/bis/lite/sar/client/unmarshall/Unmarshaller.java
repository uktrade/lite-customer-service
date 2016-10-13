package uk.gov.bis.lite.sar.client.unmarshall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.sar.util.NodeUtil;

import java.util.Optional;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Unmarshaller {

  private static final Logger LOGGER = LoggerFactory.getLogger(Unmarshaller.class);

  public Optional<String> getResponse(SOAPMessage message, String elementName, String expression) {
    try {
      final SOAPBody soapBody = message.getSOAPBody();
      XPath xpath = XPathFactory.newInstance().newXPath();
      NodeList nodeList = (NodeList) xpath.evaluate(expression, soapBody, XPathConstants.NODESET);
      if (nodeList != null) {
        return singleElementNodeResult(nodeList, xpath, elementName);
      }
      return null;
    } catch (SOAPException | XPathExpressionException e) {
      throw new RuntimeException("An error occurred while extracting the SOAP Response Body", e);
    }
  }

  private Optional<String> singleElementNodeResult(NodeList nodeList, XPath xpath, String nodeName) {
    NodeList nodes = nodeList.item(0).getChildNodes();
    Optional<String> errorCheck = NodeUtil.errorCheck(nodes, xpath);
    if (!errorCheck.isPresent()) {
      return Optional.of(NodeUtil.reduce(nodes, nodeName));
    } else {
      LOGGER.error(errorCheck.get());
    }
    return Optional.empty();
  }
}


