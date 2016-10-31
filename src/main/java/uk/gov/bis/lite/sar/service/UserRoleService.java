package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.spire.client.SpireName;
import uk.gov.bis.lite.spire.client.model.SpireRequest;


@Singleton
public class UserRoleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleService.class);

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  public static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  private SpireClient editUserRolesClient;

  @Inject
  public UserRoleService(@Named("SpireEditUserRolesClient") SpireClient editUserRolesClient) {
    this.editUserRolesClient = editUserRolesClient;
  }

  public String userRoleUpdate(UserRoleItem item) {

    // Setup Spire request
    SpireRequest request = editUserRolesClient.createRequest();
    request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_1);
    request.addChild(SpireName.ADMIN_WUA_ID, item.getAdminUserId());
    request.addChild(SpireName.USER_WUA_ID, item.getUserId());
    request.addChild(SpireName.SITE_REF, item.getSiteRef());
    request.addChild(SpireName.ROLE_TYPE, item.getRoleType());

    return (String) editUserRolesClient.getResult(request);
  }
}