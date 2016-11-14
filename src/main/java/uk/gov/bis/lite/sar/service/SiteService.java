package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.common.item.in.SiteIn;
import uk.gov.bis.lite.common.item.out.SiteOut;

import java.util.List;
import java.util.Optional;

public interface SiteService {

  String createSite(SiteIn siteItemIn, String customerId, String userId);

  List<SiteOut> getSites(String customerId, String userId);

  Optional<SiteOut> getSite(String siteId);
}

