package uk.gov.bis.lite.sar.util;

import java.util.*;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class NodeUtil {

  /**
   * converts a NodeList to java.util.List of Node
   */
  public static List<Node> list(NodeList nodeList) {
    List<Node> list = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      list.add(nodeList.item(i));
    }
    return list;
  }

  public static String reduce(NodeList nodes, String nodeName) {
    return NodeUtil.list(nodes).stream()
        .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
        .filter(node -> node.getNodeName().equals(nodeName))
        .map(NodeUtil::getText)
        .reduce("", (s, t) -> s + t);
  }

  public static Optional<String> errorCheck(NodeList nodes, XPath xpath) {
    try {
      Node errorNode = (Node) xpath.evaluate("ERROR", nodes, XPathConstants.NODE);
      if (errorNode != null) {
        return Optional.of(errorNode.getTextContent());
      }
    } catch (XPathExpressionException e) {
      return Optional.of("XPathExpressionException - an error occurred while parsing the SOAP response body.");
    }
    return Optional.empty();
  }

  public static String getText(Node node) {
    StringBuffer reply = new StringBuffer();
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      if ((child instanceof CharacterData && !(child instanceof Comment)) || child instanceof EntityReference) {
        reply.append(child.getNodeValue());
      } else if (child.getNodeType() == Node.ELEMENT_NODE) {
        reply.append(getText(child));
      }
    }
    return reply.toString();
  }
}
