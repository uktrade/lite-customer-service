package uk.gov.bis.lite.sar.spire.parsers;

import org.w3c.dom.Node;
import uk.gov.bis.lite.common.spire.client.SpireResponse;
import uk.gov.bis.lite.common.spire.client.parser.SpireParser;
import uk.gov.bis.lite.sar.spire.model.SpireSite;

import java.util.ArrayList;
import java.util.List;

public class SiteParser implements SpireParser<List<SpireSite>> {

  @Override
  public List<SpireSite> parseResponse(SpireResponse spireResponse) {
    return getSitesFromNodes(spireResponse.getElementChildNodesForList("//COMPANY_SITE_LIST"));
  }

  private List<SpireSite> getSitesFromNodes(List<Node> nodes) {
    List<SpireSite> sites = new ArrayList<>();
    for (Node node : nodes) {
      SpireSite site = new SpireSite();
      SpireResponse.getNodeValue(node, "SITE_REF").ifPresent(site::setSiteRef);
      SpireResponse.getNodeValue(node, "SAR_REF").ifPresent(site::setSarRef);
      SpireResponse.getNodeValue(node, "COMPANY_NAME").ifPresent(site::setCompanyName);
      SpireResponse.getNodeValue(node, "APPLICANT_TYPE").ifPresent(site::setApplicantType);
      SpireResponse.getNodeValue(node, "ADDRESS").ifPresent(site::setAddress);
      SpireResponse.getNodeValue(node, "DIVISION").ifPresent(site::setDivision);
      SpireResponse.getNodeValue(node, "OCCUPANCY_STATUS").ifPresent(site::setOccupancyStatus);
      SpireResponse.getNodeValue(node, "COUNTRY_REF").ifPresent(site::setCountryRef);
      sites.add(site);
    }
    return sites;
  }

}
