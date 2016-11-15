package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.item.in.UserRoleIn;
import uk.gov.bis.lite.customer.api.item.out.UsersOut;

public interface UserService {

  String userRoleUpdate(UserRoleIn item, String userId, String siteRef);

  UsersOut getCustomerAdminUsers(String customerId);
}
