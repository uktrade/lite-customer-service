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
  private final Ttl getCustomersByPostcode;
  private final Ttl getCustomersByEoriNumber;
  private final Ttl getCustomersByUserId;
  private final Ttl getCustomerById;
  private final Ttl getCustomersByCompanyNumber;
  private final Ttl getCustomersByName;

  @Inject
  public RedisCustomerServiceImpl(CustomerServiceImpl customerServiceImpl,
                                  RedissonCache redissonCache,
                                  @Named("getCustomersByPostcode") Ttl getCustomersByPostcode,
                                  @Named("getCustomersByEoriNumber") Ttl getCustomersByEoriNumber,
                                  @Named("getCustomersByUserId") Ttl getCustomersByUserId,
                                  @Named("getCustomerById") Ttl getCustomerById,
                                  @Named("getCustomersByCompanyNumber") Ttl getCustomersByCompanyNumber,
                                  @Named("getCustomersByName") Ttl getCustomersByName) {
    this.customerServiceImpl = customerServiceImpl;
    this.redissonCache = redissonCache;
    this.getCustomersByPostcode = getCustomersByPostcode;
    this.getCustomersByEoriNumber = getCustomersByEoriNumber;
    this.getCustomersByUserId = getCustomersByUserId;
    this.getCustomerById = getCustomerById;
    this.getCustomersByCompanyNumber = getCustomersByCompanyNumber;
    this.getCustomersByName = getCustomersByName;
  }

  @Override
  public Optional<CustomerView> createCustomer(CustomerParam param) {
    return customerServiceImpl.createCustomer(param);
  }

  @Override
  public List<CustomerView> getCustomersByPostcode(String postcode) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByPostcode(postcode),
        "getCustomersByPostcode",
        getCustomersByPostcode,
        postcode);
  }

  @Override
  public List<CustomerView> getCustomersByEoriNumber(String postcode, String eoriNumber) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByEoriNumber(postcode, eoriNumber),
        "getCustomersByEoriNumber",
        getCustomersByEoriNumber,
        postcode, eoriNumber);
  }

  @Override
  public List<CustomerView> getCustomersByUserId(String userId) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByUserId(userId),
        "getCustomersByUserId",
        getCustomersByUserId,
        userId);
  }

  @Override
  public Optional<CustomerView> getCustomerById(String customerId) {
    return redissonCache.getOptional(() -> customerServiceImpl.getCustomerById(customerId),
        "getCustomerById",
        getCustomerById,
        customerId);
  }

  @Override
  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByCompanyNumber(companyNumber),
        "getCustomersByCompanyNumber",
        getCustomersByCompanyNumber,
        companyNumber);
  }

  @Override
  public List<CustomerView> getCustomersByName(String companyName) {
    return redissonCache.get(() -> customerServiceImpl.getCustomersByName(companyName),
        "getCustomersByName",
        getCustomersByName,
        companyName);
  }

}
