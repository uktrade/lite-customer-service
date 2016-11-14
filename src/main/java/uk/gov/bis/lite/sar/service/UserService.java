package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.common.item.in.UserRoleIn;
import uk.gov.bis.lite.common.item.out.UsersOut;

public interface UserService {

  String userRoleUpdate(UserRoleIn item, String userId, String siteRef);

  UsersOut getCustomerAdminUsers(String customerId);
}
