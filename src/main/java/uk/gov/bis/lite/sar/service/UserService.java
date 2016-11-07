package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.sar.model.Users;
import uk.gov.bis.lite.sar.model.item.UserRoleItem;

public interface UserService {

  String userRoleUpdate(UserRoleItem item, String userId, String siteRef);

  Users getCustomerAdminUsers(String customerId);
}
