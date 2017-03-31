package uk.gov.bis.lite.customer.mocks.permissions;

import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.UserView;
import uk.gov.bis.lite.customer.service.UserService;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class MockUserService implements UserService {

  private boolean failUpdateUserRole;

  private boolean missingCustomerAdminUsers;

  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    if (!failUpdateUserRole) {
      return "COMPLETE";
    }
    return "ERROR";
  }

  public Optional<UsersResponse> getCustomerAdminUsers(String customerId) {
    if (missingCustomerAdminUsers) {
      return Optional.empty();
    } else {
      List<UserView> userViews = new ArrayList<>();
      UserView userView = new UserView();
      userView.setFullName("Admin User");
      userView.setForename("Admin");
      userView.setSurname("User");
      userView.setEmailAddress("admin@test.com");
      userView.setRoleName("SAR_ADMINISTRATOR");
      userView.setUserId("EXISTING");
      userViews.add(userView);
      return Optional.of(new UsersResponse(userViews));
    }
  }

  /**
   * Pact State setters
   */
  public void setFailUpdateUserRole(boolean failUpdateUserRole) {
    this.failUpdateUserRole = failUpdateUserRole;
  }

  public void setMissingCustomerAdminUsers(boolean missingCustomerAdminUsers) {
    this.missingCustomerAdminUsers = missingCustomerAdminUsers;
  }
}
