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
  private final Ttl getSite;
  private final Ttl getSitesByUserId;
  private final Ttl getSitesByCustomerId;

  @Inject
  public RedisSiteServiceImpl(SiteServiceImpl siteServiceImpl,
                              RedissonCache redissonCache,
                              @Named("getSite") Ttl getSite,
                              @Named("getSitesByUserId") Ttl getSitesByUserId,
                              @Named("getSitesByCustomerId") Ttl getSitesByCustomerId) {
    this.siteServiceImpl = siteServiceImpl;
    this.redissonCache = redissonCache;
    this.getSite = getSite;
    this.getSitesByUserId = getSitesByUserId;
    this.getSitesByCustomerId = getSitesByCustomerId;
  }

  @Override
  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    return siteServiceImpl.createSite(param, customerId, userId);
  }

  @Override
  public Optional<SiteView> getSite(String siteId) {
    return redissonCache.getOptional(() -> siteServiceImpl.getSite(siteId),
        "getSite",
        getSite,
        siteId);
  }

  @Override
  public List<SiteView> getSitesByUserId(String customerId, String userId) {
    return redissonCache.get(() -> siteServiceImpl.getSitesByUserId(customerId, userId),
        "getSitesByUserId",
        getSitesByUserId,
        customerId, userId);
  }

  @Override
  public List<SiteView> getSitesByCustomerId(String customerId) {
    return redissonCache.get(() -> siteServiceImpl.getSitesByCustomerId(customerId),
        "getSitesByCustomerId",
        getSitesByCustomerId,
        customerId);
  }

}
