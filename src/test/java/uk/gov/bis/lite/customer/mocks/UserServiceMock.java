package uk.gov.bis.lite.customer.mocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.UserView;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class UserServiceMock implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private UsersResponse mockUsers;
  private String mockRef;

  public UserServiceMock(String mockRef, int numberOfUserDetails) {
    this.mockRef = mockRef;
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

  public String userRoleUpdate(UserRoleParam item, String userId, String siteRef) {
    return mockRef;
  }

  public UsersResponse getCustomerAdminUsers(String customerId) {
    LOGGER.info("mockUsers: " + mockUsers.getAdministrators().size());
    return mockUsers;
  }
}
