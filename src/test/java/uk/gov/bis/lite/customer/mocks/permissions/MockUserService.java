package uk.gov.bis.lite.customer.mocks.permissions;

import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.service.UserService;

import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class MockUserService implements UserService {

  private boolean failUpdateUserRole = false;

  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    if(!failUpdateUserRole) {
      return "COMPLETE";
    }
    return "ERROR";
  }

  public Optional<UsersResponse> getCustomerAdminUsers(String customerId) {
    return Optional.empty();
  }

  /**
   * Pact State setters
   */
  public void setFailUpdateUserRole(boolean failUpdateUserRole) {
    this.failUpdateUserRole = failUpdateUserRole;
  }
}
