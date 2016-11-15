package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;

import java.util.List;
import java.util.Optional;

public interface SiteService {

  Optional<SiteView> createSite(SiteParam param, String customerId, String userId);

  Optional<SiteView> getSite(String siteId);

  List<SiteView> getSites(String customerId, String userId);

}

