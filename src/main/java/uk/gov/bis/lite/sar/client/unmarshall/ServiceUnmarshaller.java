package uk.gov.bis.lite.sar.client.unmarshall;

import org.w3c.dom.NodeList;

import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

abstract class ServiceUnmarshaller {

  List<?> execute(SOAPMessage message, String companyName, String mainTag) {

    final SOAPBody soapBody;
    NodeList nodeList;
    try {
      soapBody = message.getSOAPBody();
      XPath xpath = XPathFactory.newInstance().newXPath();
      nodeList = (NodeList) xpath.evaluate(mainTag, soapBody, XPathConstants.NODESET);
      if (nodeList != null) {
        return parseSoapBody(nodeList, xpath, companyName);
      }
      return null;
    } catch (SOAPException | XPathExpressionException e) {
      throw new RuntimeException("An error occurred while extracting the SOAP Response Body", e);
    }
  }

  protected abstract List<?> parseSoapBody(NodeList nodeList, XPath xpath, String reference);
}
