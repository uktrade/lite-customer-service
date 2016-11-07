package uk.gov.bis.lite.sar.mocks;

import uk.gov.bis.lite.sar.model.UserDetail;
import uk.gov.bis.lite.sar.model.Users;
import uk.gov.bis.lite.sar.model.item.UserRoleItem;
import uk.gov.bis.lite.sar.service.UserService;
import uk.gov.bis.lite.sar.spire.model.SpireUserDetail;

import java.util.ArrayList;
import java.util.List;

public class UserServiceMock implements UserService {

  private Users mockUsers;
  private String mockRef;

  public UserServiceMock(String mockRef, int numberOfUserDetails) {
    this.mockRef = mockRef;
    initUsers(numberOfUserDetails);
  }

  private void initUsers(int numberOfUserDetails) {
    List<UserDetail> userDetails = new ArrayList<>();
    for (int i = 0; i < numberOfUserDetails; i++) {
      SpireUserDetail spireDetail = new SpireUserDetail();
      spireDetail.setUserId("userId" + i);
      userDetails.add(new UserDetail(spireDetail));
    }
    this.mockUsers = new Users(userDetails);
  }

  public String userRoleUpdate(UserRoleItem item, String userId, String siteRef) {
    return mockRef;
  }

  public Users getCustomerAdminUsers(String customerId) {
    return mockUsers;
  }
}
