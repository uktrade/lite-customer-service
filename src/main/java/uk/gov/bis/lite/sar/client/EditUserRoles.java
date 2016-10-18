package uk.gov.bis.lite.sar.client;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.xml.soap.SOAPMessage;

public class EditUserRoles extends SpireClient {

  private static final String VERSION_NO = "1.1";
  private static final String NAMESPACE = "SPIRE_EDIT_USER_ROLES";
  private static final String CHILD_NAME = "USER_DETAILS";

  private static final String ROLE_TYPE_ADMIN = "ADMIN";
  private static final String ROLE_TYPE_SUBMITTER = "SUBMITTER";
  private static final String ROLE_TYPE_PREPARER = "PREPARER";

  @Inject
  public EditUserRoles(@Named("editUserRolesUrl") String url,
                          @Named("soapUserName") String userName,
                          @Named("soapPassword") String password) {
    super(url, userName, password);
  }

  public SOAPMessage updateUserSiteToAdmin(String userId, String roleType, String adminUserId, String siteRef) {
    SOAPMessage request = getRequest(NAMESPACE, CHILD_NAME);
    addChild(request, "VERSION_NO", VERSION_NO);
    addChild(request, "ADMIN_WUA_ID", adminUserId);
    addChild(request, "USER_WUA_ID", userId);
    addChild(request, "SITE_REF", siteRef);
    addChild(request, "ROLE_TYPE", roleType);
    return getResponse(request);
  }

}
