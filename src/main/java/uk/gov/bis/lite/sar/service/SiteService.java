package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.SiteClient;
import uk.gov.bis.lite.sar.client.unmarshall.SiteUnmarshaller;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.SiteItem;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.sar.model.spire.SpireSite;
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireEndpoint;
import uk.gov.bis.lite.spire.SpireException;
import uk.gov.bis.lite.spire.SpireName;
import uk.gov.bis.lite.spire.SpireRequest;
import uk.gov.bis.lite.spire.SpireResponse;
import uk.gov.bis.lite.spire.SpireUnmarshaller;
import uk.gov.bis.lite.spire.SpireUtil;
import uk.gov.bis.lite.spireclient.SpireClientService;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;


@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SiteClient client;
  private SiteUnmarshaller siteUnmarshaller;
  private SpireClientService spireClient;

  private SpireClient spClient;
  private SpireUnmarshaller spireUnmarshaller;

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  private static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  @Inject
  public SiteService(SiteClient client, SiteUnmarshaller siteUnmarshaller, SpireClientService spireClient,
                     SpireClient spClient, SpireUnmarshaller spireUnmarshaller) {
    this.client = client;
    this.siteUnmarshaller = siteUnmarshaller;
    this.spireClient = spireClient;
    this.spClient = spClient;
    this.spireUnmarshaller = spireUnmarshaller;
  }

  public String createSite(SiteItem item) {
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup Spire request
      SpireRequest request = spClient.createRequest(SpireEndpoint.CREATE_SITE_FOR_SAR);
      request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_0);
      request.addChild(SpireName.WUA_ID, item.getUserId());
      request.addChild(SpireName.SAR_REF, item.getSarRef());
      request.addChild(SpireName.DIVISION, item.getSiteName());
      request.addChild(SpireName.LITE_ADDRESS, SpireUtil.getAddressItemJson(item.getAddressItem()));
      request.addChild(SpireName.ADDRESS, SpireUtil.getFriendlyAddress(item.getAddressItem()));
      request.addChild(SpireName.COUNTRY_REF, item.getAddressItem().getCountry());

      // Get Response and unmarshall
      SpireResponse response = spClient.sendRequest(request);
      return spireUnmarshaller.getSingleResponseElementContent(response);
    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public String userRoleUpdate(UserRoleItem item) {

    // Setup Spire request
    SpireRequest request = spClient.createRequest(SpireEndpoint.EDIT_USER_ROLES);
    request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_1);
    request.addChild(SpireName.ADMIN_WUA_ID, item.getAdminUserId());
    request.addChild(SpireName.USER_WUA_ID, item.getUserId());
    request.addChild(SpireName.SITE_REF, item.getSiteRef());
    request.addChild(SpireName.ROLE_TYPE, item.getRoleType());

    // Get Response and unmarshall
    SpireResponse response = spClient.sendRequest(request);
    return spireUnmarshaller.getSingleResponseElementContent(response);
  }

  public List<Site> getSites(String customerId, String userId) {
    final SOAPMessage soapMessage = client.getSitesByCustomerIdUserId(customerId, userId);
    List<SpireSite> spireSites = siteUnmarshaller.execute(soapMessage);
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
