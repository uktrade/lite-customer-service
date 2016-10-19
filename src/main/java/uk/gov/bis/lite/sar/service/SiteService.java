package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.CreateSiteForSar;
import uk.gov.bis.lite.sar.client.EditUserRoles;
import uk.gov.bis.lite.sar.client.SiteClient;
import uk.gov.bis.lite.sar.client.unmarshall.SiteUnmarshaller;
import uk.gov.bis.lite.sar.client.unmarshall.Unmarshaller;
import uk.gov.bis.lite.sar.exception.CreateException;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.sar.model.SiteItem;
import uk.gov.bis.lite.sar.model.spire.SpireSite;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SiteClient client;
  private SiteUnmarshaller siteUnmarshaller;
  private CreateSiteForSar createSiteForSar;
  private EditUserRoles editUserRoles;
  private Unmarshaller unmarshaller;

  private static final String CSFS_RESPONSE_ELEMENT_NAME = "SITE_REF";
  private static final String EUR_RESPONSE_ELEMENT_NAME = "RESULT";
  private static final String RESPONSE_XPATH_EXPRESSION = "//*[local-name()='RESPONSE']";

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  private static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  @Inject
  public SiteService(SiteClient client,
                     CreateSiteForSar createSiteForSar,
                     EditUserRoles editUserRoles,
                     SiteUnmarshaller siteUnmarshaller,
                     Unmarshaller unmarshaller) {
    this.client = client;
    this.createSiteForSar = createSiteForSar;
    this.editUserRoles = editUserRoles;
    this.siteUnmarshaller = siteUnmarshaller;
    this.unmarshaller = unmarshaller;
  }

  public String userRoleUpdate(UserRoleItem item) {
    SOAPMessage message = editUserRoles.updateUserSiteToAdmin(
        item.getUserId(),
        item.getRoleType(),
        item.getAdminUserId(),
        item.getSiteRef());
    return unmarshaller.getResponse(message, EUR_RESPONSE_ELEMENT_NAME, RESPONSE_XPATH_EXPRESSION);
  }

  public String createSite(SiteItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SOAPMessage message = createSiteForSar.createSite(item);
      return unmarshaller.getResponse(message, CSFS_RESPONSE_ELEMENT_NAME, RESPONSE_XPATH_EXPRESSION);
    } else {
      throw new CreateException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Site> getSites(String customerId, String userId) {
    final SOAPMessage soapMessage = client.getSitesByCustomerIdUserId(customerId, userId);
    List<SpireSite> spireSites = siteUnmarshaller.execute(soapMessage);
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
