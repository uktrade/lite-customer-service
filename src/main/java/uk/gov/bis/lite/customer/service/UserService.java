package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.UsersResponse;

public interface UserService {

  String userRoleUpdate(UserRoleParam item, String userId, String siteRef);

  UsersResponse getCustomerAdminUsers(String customerId);
}
