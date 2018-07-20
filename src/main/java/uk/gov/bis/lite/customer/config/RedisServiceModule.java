package uk.gov.bis.lite.customer.config;

import com.google.inject.AbstractModule;
import ru.vyarus.dropwizard.guice.module.support.ConfigurationAwareModule;
import uk.gov.bis.lite.common.redis.RedisCacheModule;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.RedisCustomerServiceImpl;
import uk.gov.bis.lite.customer.service.RedisSiteServiceImpl;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

public class RedisServiceModule extends AbstractModule implements ConfigurationAwareModule<CustomerApplicationConfiguration> {

  private CustomerApplicationConfiguration customerApplicationConfiguration;

  @Override
  protected void configure() {
    install(new RedisCacheModule(customerApplicationConfiguration.getRedisConfiguration()));

    bind(CustomerService.class).to(RedisCustomerServiceImpl.class);
    bind(SiteService.class).to(RedisSiteServiceImpl.class);
    bind(UserService.class).to(UserServiceImpl.class);
  }

  @Override
  public void setConfiguration(CustomerApplicationConfiguration customerApplicationConfiguration) {
    this.customerApplicationConfiguration = customerApplicationConfiguration;
  }

}
