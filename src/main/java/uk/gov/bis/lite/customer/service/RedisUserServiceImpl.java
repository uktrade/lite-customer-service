package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import uk.gov.bis.lite.common.redis.RedissonCache;
import uk.gov.bis.lite.common.redis.Ttl;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;

import java.util.Optional;

public class RedisUserServiceImpl implements UserService {

  private final UserServiceImpl userServiceImpl;
  private final RedissonCache redissonCache;
  private final Ttl getCustomerAdminUsers;

  @Inject
  public RedisUserServiceImpl(UserServiceImpl userServiceImpl, RedissonCache redissonCache,
                              @Named("getCustomerAdminUsers") Ttl getCustomerAdminUsers) {
    this.userServiceImpl = userServiceImpl;
    this.redissonCache = redissonCache;
    this.getCustomerAdminUsers = getCustomerAdminUsers;
  }

  @Override
  public String userRoleUpdate(UserRoleParam param, String userId, String siteRef) {
    return userServiceImpl.userRoleUpdate(param, userId, siteRef);
  }

  @Override
  public Optional<UsersResponse> getCustomerAdminUsers(String customerId) {
    return redissonCache.getOptional(() -> userServiceImpl.getCustomerAdminUsers(customerId),
        "getCustomerAdminUsers",
        getCustomerAdminUsers,
        customerId);
  }

}
