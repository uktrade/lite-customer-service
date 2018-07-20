package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import uk.gov.bis.lite.common.redis.RedissonCache;
import uk.gov.bis.lite.common.redis.Ttl;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;

import java.util.List;
import java.util.Optional;

public class RedisSiteServiceImpl implements SiteService {

  private final SiteServiceImpl siteServiceImpl;
  private final RedissonCache redissonCache;
  private final Ttl getSiteTtl;
  private final Ttl getSitesByUserIdTtl;
  private final Ttl getSitesByCustomerIdTtl;

  @Inject
  public RedisSiteServiceImpl(SiteServiceImpl siteServiceImpl,
                              RedissonCache redissonCache,
                              @Named("getSiteTtl") Ttl getSiteTtl,
                              @Named("getSitesByUserIdTtl") Ttl getSitesByUserIdTtl,
                              @Named("getSitesByCustomerIdTtl") Ttl getSitesByCustomerIdTtl) {
    this.siteServiceImpl = siteServiceImpl;
    this.redissonCache = redissonCache;
    this.getSiteTtl = getSiteTtl;
    this.getSitesByUserIdTtl = getSitesByUserIdTtl;
    this.getSitesByCustomerIdTtl = getSitesByCustomerIdTtl;
  }

  @Override
  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    return siteServiceImpl.createSite(param, customerId, userId);
  }

  @Override
  public Optional<SiteView> getSite(String siteId) {
    return redissonCache.getOptional(() -> siteServiceImpl.getSite(siteId),
        "getSite",
        getSiteTtl,
        siteId);
  }

  @Override
  public List<SiteView> getSitesByUserId(String customerId, String userId) {
    return redissonCache.get(() -> siteServiceImpl.getSitesByUserId(customerId, userId),
        "getSitesByUserId",
        getSitesByUserIdTtl,
        customerId, userId);
  }

  @Override
  public List<SiteView> getSitesByCustomerId(String customerId) {
    return redissonCache.get(() -> siteServiceImpl.getSitesByCustomerId(customerId),
        "getSitesByCustomerId",
        getSitesByCustomerIdTtl,
        customerId);
  }

}
