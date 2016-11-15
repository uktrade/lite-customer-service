package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;

import java.util.List;
import java.util.Optional;

public interface SiteService {

  String createSite(SiteParam siteItemIn, String customerId, String userId);

  List<SiteView> getSites(String customerId, String userId);

  Optional<SiteView> getSite(String siteId);
}

