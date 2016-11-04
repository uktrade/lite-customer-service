package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.item.SiteItem;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.spire.SpireSiteClient;
import uk.gov.bis.lite.sar.util.Util;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SpireReferenceClient createSiteForSarReferenceClient;
  private SpireSiteClient siteClient;

  @Inject
  public SiteService(@Named("SpireCreateSiteForSarClient") SpireReferenceClient createSiteForSarReferenceClient,
                     SpireSiteClient siteClient) {
    this.createSiteForSarReferenceClient = createSiteForSarReferenceClient;
    this.siteClient = siteClient;
  }

  public String createSite(SiteItem item) {
    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SpireRequest request = createSiteForSarReferenceClient.createRequest();
      Util.addChild(request, "VERSION_NO", "1.0");
      Util.addChild(request, "WUA_ID", item.getUserId());
      Util.addChild(request, "SAR_REF", item.getSarRef());
      Util.addChild(request, "DIVISION", item.getSiteName());
      Util.addChild(request, "LITE_ADDRESS", Util.getAddressItemJson(item.getAddressItem()));
      Util.addChild(request, "ADDRESS", Util.getFriendlyAddress(item.getAddressItem()));
      Util.addChild(request, "COUNTRY_REF", item.getAddressItem().getCountry());
      return createSiteForSarReferenceClient.sendRequest(request);
    } else {
      throw new SpireClientException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Site> getSites(String customerId, String userId) {
    SpireRequest request = siteClient.createRequest();
    Util.addChild(request, "userId", userId);
    Util.addChild(request, "sarRef", customerId);
    return siteClient.sendRequest(request).stream().map(Site::new).collect(Collectors.toList());
  }
}
