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
import uk.gov.bis.lite.spireclient.SpireClientService;
import uk.gov.bis.lite.spireclient.model.SpireRequest;
import uk.gov.bis.lite.spireclient.model.SpireResponse;
import uk.gov.bis.lite.spireclient.spire.SpireException;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SiteClient client;
  private SiteUnmarshaller siteUnmarshaller;
  private SpireClientService spireClient;

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  private static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  @Inject
  public SiteService(SiteClient client,
                     SiteUnmarshaller siteUnmarshaller,
                     SpireClientService spireClient) {
    this.client = client;
    this.siteUnmarshaller = siteUnmarshaller;
    this.spireClient = spireClient;
  }

  public String userRoleUpdate(UserRoleItem item) {

    // Setup Spire request
    SpireRequest request = spireClient.getSpireRequest(SpireClientService.Endpoint.EDIT_USER_ROLES, item.getUserId());
    request.setAdminUserId(item.getAdminUserId());
    request.setSiteRef(item.getSiteRef());
    request.setRoleType(item.getRoleType());

    // Execute Spire Request
    SpireResponse response = spireClient.executeRequest(request);
    LOGGER.info("SpireResponse: " + response.getInfo());
    return response.getRef();
  }

  public String createSite(SiteItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup Spire request
      SpireRequest request = spireClient.getSpireRequest(SpireClientService.Endpoint.CREATE_SITE_FOR_SAR, item.getUserId());
      request.setAddressData(item.getAddressItem());
      request.setSarRef(item.getSarRef());
      request.setSiteName(item.getSiteName());

      // Execute Spire Request
      SpireResponse response = spireClient.executeRequest(request);
      LOGGER.info("SpireResponse: " + response.getInfo());
      return response.getRef();

    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Site> getSites(String customerId, String userId) {
    final SOAPMessage soapMessage = client.getSitesByCustomerIdUserId(customerId, userId);
    List<SpireSite> spireSites = siteUnmarshaller.execute(soapMessage);
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
