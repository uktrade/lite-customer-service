package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.SiteItem;
import uk.gov.bis.lite.sar.util.Util;
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireName;
import uk.gov.bis.lite.spire.SpireRequest;
import uk.gov.bis.lite.spire.SpireResponse;
import uk.gov.bis.lite.spire.SpireUnmarshaller;
import uk.gov.bis.lite.spire.exception.SpireException;
import uk.gov.bis.lite.spire.model.SpireSite;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SpireClient spireClient;
  private SpireUnmarshaller spireUnmarshaller;


  @Inject
  public SiteService(SpireClient spireClient, SpireUnmarshaller spireUnmarshaller) {
    this.spireClient = spireClient;
    this.spireUnmarshaller = spireUnmarshaller;
  }

  public String createSite(SiteItem item) {

    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup SpireRequest
      SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.CREATE_SITE_FOR_SAR);
      request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_0);
      request.addChild(SpireName.WUA_ID, item.getUserId());
      request.addChild(SpireName.SAR_REF, item.getSarRef());
      request.addChild(SpireName.DIVISION, item.getSiteName());
      request.addChild(SpireName.LITE_ADDRESS, Util.getAddressItemJson(item.getAddressItem()));
      request.addChild(SpireName.ADDRESS, Util.getFriendlyAddress(item.getAddressItem()));
      request.addChild(SpireName.COUNTRY_REF, item.getAddressItem().getCountry());

      // Get SpireResponse and unmarshall
      SpireResponse response = spireClient.sendRequest(request);
      return spireUnmarshaller.getSingleResponseElementContent(response);
    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Site> getSites(String customerId, String userId) {

    // Setup SpireRequest
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.COMPANY_SITES);
    request.addChild(SpireName.userId, userId);
    request.addChild(SpireName.sarRef, customerId);

    // Get SpireResponse and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    List<SpireSite> spireSites = spireUnmarshaller.getSpireSites(response);

    // Map SpireCompany to Customer
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
