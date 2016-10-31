package uk.gov.bis.lite.sar.spire;

import static uk.gov.bis.lite.spire.client.SpireName.ADDRESS;
import static uk.gov.bis.lite.spire.client.SpireName.APPLICANT_TYPE;
import static uk.gov.bis.lite.spire.client.SpireName.COMPANY_NAME;
import static uk.gov.bis.lite.spire.client.SpireName.DIVISION;
import static uk.gov.bis.lite.spire.client.SpireName.OCCUPANCY_STATUS;
import static uk.gov.bis.lite.spire.client.SpireName.SAR_REF;
import static uk.gov.bis.lite.spire.client.SpireName.SITE_REF;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.gov.bis.lite.spire.client.model.SpireResponse;
import uk.gov.bis.lite.spire.client.model.SpireSite;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class CompanySiteParser extends SpireParser<List<SpireSite>> {

  public List<SpireSite> getResult(SpireResponse spireResponse) {
    return parseCompanySites(getNodeList(spireResponse.getMessage(), "//COMPANY_SITE_LIST"));
  }

  private List<SpireSite> parseCompanySites(NodeList nodeList) {
    List<SpireSite> sites = new ArrayList<>();
    XPath xpath = XPathFactory.newInstance().newXPath();
    NodeList nodes = nodeList.item(0).getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      SpireSite site = new SpireSite();
      Node node = nodes.item(i).cloneNode(true);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        getValue(node, xpath, SITE_REF).ifPresent(site::setSiteRef);
        getValue(node, xpath, SAR_REF).ifPresent(site::setSarRef);
        getValue(node, xpath, COMPANY_NAME).ifPresent(site::setCompanyName);
        getValue(node, xpath, APPLICANT_TYPE).ifPresent(site::setApplicantType);
        getValue(node, xpath, ADDRESS).ifPresent(site::setAddress);
        getValue(node, xpath, DIVISION).ifPresent(site::setDivision);
        getValue(node, xpath, OCCUPANCY_STATUS).ifPresent(site::setOccupancyStatus);
        sites.add(site);
      }
    }
    return sites;
  }
}
