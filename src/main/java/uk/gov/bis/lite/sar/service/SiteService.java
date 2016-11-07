package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.item.SiteItem;

import java.util.List;

public interface SiteService {

  String createSite(SiteItem item);

  List<Site> getSites(String customerId, String userId);
}
