package uk.gov.bis.lite.customer.mocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.item.in.UserRoleIn;
import uk.gov.bis.lite.customer.api.item.out.UserOut;
import uk.gov.bis.lite.customer.api.item.out.UsersOut;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class UserServiceMock implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private UsersOut mockUsers;
  private String mockRef;

  public UserServiceMock(String mockRef, int numberOfUserDetails) {
    this.mockRef = mockRef;
    initUsers(numberOfUserDetails);
  }

  private void initUsers(int numberOfUserDetails) {
    List<UserOut> userOuts = new ArrayList<>();
    for (int i = 0; i < numberOfUserDetails; i++) {
      UserOut userOut = new UserOut();
      userOuts.add(userOut);
    }
    this.mockUsers = new UsersOut(userOuts);
  }

  public String userRoleUpdate(UserRoleIn item, String userId, String siteRef) {
    return mockRef;
  }

  public UsersOut getCustomerAdminUsers(String customerId) {
    LOGGER.info("mockUsers: " + mockUsers.getAdministrators().size());
    return mockUsers;
  }
}
