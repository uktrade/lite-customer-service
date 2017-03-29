package uk.gov.bis.lite.customer.mocks.permissions;

import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.service.UserService;

import java.util.Optional;

public class MockUserService implements UserService, PermissionsPactConstants {

  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    if(userId.equals(USER_ID_SUCCESS)) {
      return "COMPLETE";
    }
    return "ERROR";
  }

  public Optional<UsersResponse> getCustomerAdminUsers(String customerId) {
    return Optional.empty();
  }
}
