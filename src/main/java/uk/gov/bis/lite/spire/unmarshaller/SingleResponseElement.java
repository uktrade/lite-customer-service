package uk.gov.bis.lite.spire.unmarshaller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.spire.SpireName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class SingleResponseElement {

  private static final Logger LOGGER = LoggerFactory.getLogger(SingleResponseElement.class);

  public String getSpireResponse(SOAPMessage message, String responseElementName) {
    String ref = "";
    try {
      final SOAPBody soapBody = message.getSOAPBody();
      XPath xpath = XPathFactory.newInstance().newXPath();
      NodeList nodeList = (NodeList) xpath.evaluate(SpireName.SAR_XPATH_EXP, soapBody, XPathConstants.NODESET);
      if (nodeList != null && nodeList.item(0) != null) {
        NodeList nodes = nodeList.item(0).getChildNodes();
        String reference = reduce(nodes, responseElementName);
        if(reference != null && !reference.isEmpty()) {
          ref = reference;
        }
      }
    } catch (SOAPException | XPathExpressionException e) {
      LOGGER.error("", e);
    }
    return ref;
  }

  private String reduce(NodeList nodes, String nodeName) {
    return list(nodes).stream()
        .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
        .filter(node -> node.getNodeName().equals(nodeName))
        .map(this::getText)
        .collect(Collectors.joining());
  }

  private List<Node> list(NodeList nodeList) {
    List<Node> list = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      list.add(nodeList.item(i));
    }
    return list;
  }
  private String getText(Node node) {
    StringBuilder reply = new StringBuilder();
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if ((isCharacterData(child) && !isComment(child)) || isEntityReference(child)) {
        reply.append(child.getNodeValue());
      } else if (isElementNode(child)) {
        reply.append(getText(child));
      }
    }
    return reply.toString();
  }

  private boolean isEntityReference(Node node) {
    return node instanceof EntityReference;
  }

  private boolean isComment(Node node) {
    return node instanceof Comment;
  }

  private boolean isCharacterData(Node node) {
    return node instanceof CharacterData;
  }

  private boolean isElementNode(Node node) {
    return node.getNodeType() == Node.ELEMENT_NODE;
  }
}
