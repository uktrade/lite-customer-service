package uk.gov.bis.lite.customer;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import uk.gov.bis.lite.customer.mocks.permissions.MockCustomerService;
import uk.gov.bis.lite.customer.mocks.permissions.MockSiteService;
import uk.gov.bis.lite.customer.mocks.permissions.MockUserService;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.UserService;

public class TestServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CustomerService.class).to(MockCustomerService.class).in(Scopes.SINGLETON);
    bind(SiteService.class).to(MockSiteService.class).in(Scopes.SINGLETON);
    bind(UserService.class).to(MockUserService.class).in(Scopes.SINGLETON);
  }

}
