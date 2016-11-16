package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.UserView;
import uk.gov.bis.lite.customer.spire.SpireReferenceClient;
import uk.gov.bis.lite.customer.spire.SpireUserDetailClient;
import uk.gov.bis.lite.customer.spire.model.SpireUserDetail;

import java.util.List;
import java.util.stream.Collectors;


@Singleton
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  public static final String USER_ROLE_UPDATE_STATUS_COMPLETE = "COMPLETE";
  public static final String USER_ROLE_UPDATE_STATUS_ERROR = "Error";

  public static final String SPIRE_ROLE_APPLICATION_CONTACT = "APPLICATION_CONTACT";
  public static final String SPIRE_ROLE_APPLICATION_PREPARER = "APPLICATION_PREPARER";
  public static final String SPIRE_ROLE_APPLICATION_SUBMITTER = "APPLICATION_SUBMITTER";
  public static final String SPIRE_ROLE_LICENCE_RETURN_CONTACT = "LICENCE_RETURN_CONTACT";
  public static final String SPIRE_ROLE_SAR_ADMINISTRATOR = "SAR_ADMINISTRATOR";

  private SpireReferenceClient editUserRolesClient;
  private SpireUserDetailClient userDetailClient;

  @Inject
  public UserServiceImpl(@Named("SpireEditUserRolesClient") SpireReferenceClient editUserRolesClient,
                         SpireUserDetailClient userDetailClient) {
    this.editUserRolesClient = editUserRolesClient;
    this.userDetailClient = userDetailClient;
  }

  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    SpireRequest request = editUserRolesClient.createRequest();
    request.addChild("VERSION_NO", "1.1");
    request.addChild("ADMIN_WUA_ID", param.getAdminUserId());
    request.addChild("USER_WUA_ID", userId);
    request.addChild("SITE_REF", siteRef);
    request.addChild("ROLE_TYPE", param.getRoleType().name());
    return editUserRolesClient.sendRequest(request);
  }

  public UsersResponse getCustomerAdminUsers(String customerId) {
    SpireRequest request = userDetailClient.createRequest();
    request.addChild("sarRef", customerId);
    List<SpireUserDetail> spireUserDetails = userDetailClient.sendRequest(request);
    List<UserView> adminUserDetails = spireUserDetails.stream()
        .filter(sud -> sud.getRoleName().equals(SPIRE_ROLE_SAR_ADMINISTRATOR))
        .map(this::getUserOut)
        .collect(Collectors.toList());

    LOGGER.info("adminUserDetails: " + adminUserDetails.size());
    return new UsersResponse(adminUserDetails);
  }

  private UserView getUserOut(SpireUserDetail spireUserDetail) {
    UserView userView = new UserView();
    userView.setEmailAddress(spireUserDetail.getEmailAddress());
    userView.setUserId(spireUserDetail.getUserId());
    userView.setForename(spireUserDetail.getForename());
    userView.setFullName(spireUserDetail.getFullName());
    userView.setRoleName(spireUserDetail.getRoleName());
    return userView;
  }

  ;
}