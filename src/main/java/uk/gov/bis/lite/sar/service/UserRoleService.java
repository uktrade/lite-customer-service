package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireName;
import uk.gov.bis.lite.spire.SpireRequest;
import uk.gov.bis.lite.spire.SpireResponse;
import uk.gov.bis.lite.spire.SpireUnmarshaller;


@Singleton
public class UserRoleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleService.class);

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  public static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  private SpireClient spireClient;
  private SpireUnmarshaller spireUnmarshaller;

  @Inject
  public UserRoleService(SpireClient spireClient, SpireUnmarshaller spireUnmarshaller) {
    this.spireClient = spireClient;
    this.spireUnmarshaller = spireUnmarshaller;
  }

  public String userRoleUpdate(UserRoleItem item) {

    // Setup Spire request
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.EDIT_USER_ROLES);
    request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_1);
    request.addChild(SpireName.ADMIN_WUA_ID, item.getAdminUserId());
    request.addChild(SpireName.USER_WUA_ID, item.getUserId());
    request.addChild(SpireName.SITE_REF, item.getSiteRef());
    request.addChild(SpireName.ROLE_TYPE, item.getRoleType());

    // Get Response and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    return spireUnmarshaller.getSingleResponseElementContent(response);
  }
}