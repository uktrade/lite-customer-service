package uk.gov.bis.lite.customer.mocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.UserView;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceMock implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private UsersResponse mockUsers;

  public UserServiceMock(int numberOfUserDetails) {
    initUsers(numberOfUserDetails);
  }

  private void initUsers(int numberOfUserDetails) {
    List<UserView> userViews = new ArrayList<>();
    for (int i = 0; i < numberOfUserDetails; i++) {
      UserView userView = new UserView();
      userViews.add(userView);
    }
    this.mockUsers = new UsersResponse(userViews);
  }

  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    return "COMPLETE";
  }

  public Optional<UsersResponse> getCustomerAdminUsers(String customerId) {
    LOGGER.info("mockUsers: " + mockUsers.getAdministrators().size());
    return Optional.of(mockUsers);
  }
}
