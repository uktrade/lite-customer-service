package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import uk.gov.bis.lite.common.redis.RedissonCache;
import uk.gov.bis.lite.common.redis.Ttl;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;

import java.util.List;
import java.util.Optional;

@Singleton
public class RedisCustomerServiceImpl implements CustomerService {

  private final CustomerServiceImpl customerServiceImpl;
  private final RedissonCache redissonCache;
  private final Ttl getCustomersByUserIdTtl;
  private final Ttl getCustomerByIdTtl;

  @Inject
  public RedisCustomerServiceImpl(CustomerServiceImpl customerServiceImpl,
                                  RedissonCache redissonCache,
                                  @Named("getCustomersByUserIdTtl") Ttl getCustomersByUserIdTtl,
                                  @Named("getCustomerByIdTtl") Ttl getCustomerByIdTtl) {
    this.customerServiceImpl = customerServiceImpl;
    this.redissonCache = redissonCache;
    this.getCustomersByUserIdTtl = getCustomersByUserIdTtl;
    this.getCustomerByIdTtl = getCustomerByIdTtl;
  }

  @Override
  public Optional<CustomerView> createCustomer(CustomerParam param) {
    return customerServiceImpl.createCustomer(param);
  }

  @Override
  public List<CustomerView> getCustomersByPostcode(String postcode) {
    return customerServiceImpl.getCustomersByPostcode(postcode);
  }

  @Override
  public List<CustomerView> getCustomersByEoriNumber(String postcode, String eoriNumber) {
    return customerServiceImpl.getCustomersByEoriNumber(postcode, eoriNumber);
  }

  @Override
  public List<CustomerView> getCustomersByUserId(String userId) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByUserId(userId),
        "getCustomersByUserId",
        getCustomersByUserIdTtl,
        userId);
  }

  @Override
  public Optional<CustomerView> getCustomerById(String customerId) {
    return redissonCache.getOptional(() -> customerServiceImpl.getCustomerById(customerId),
        "getCustomerById",
        getCustomerByIdTtl,
        customerId);
  }

  @Override
  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    return customerServiceImpl.getCustomersByCompanyNumber(companyNumber);
  }

  @Override
  public List<CustomerView> getCustomersByName(String companyName) {
    return customerServiceImpl.getCustomersByName(companyName);
  }

}
