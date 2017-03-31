package uk.gov.bis.lite.customer;

import com.google.inject.Scopes;
import uk.gov.bis.lite.customer.config.guice.GuiceModule;
import uk.gov.bis.lite.customer.mocks.CustomerServiceMock;
import uk.gov.bis.lite.customer.mocks.permissions.MockSiteService;
import uk.gov.bis.lite.customer.mocks.permissions.MockUserService;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.UserService;

public class GuiceTestModule extends GuiceModule {

  @Override
  protected void configure() {
    bind(CustomerService.class).to(CustomerServiceMock.class);
    bind(SiteService.class).to(MockSiteService.class);
    bind(MockSiteService.class).in(Scopes.SINGLETON);
    bind(UserService.class).to(MockUserService.class);
    bind(MockUserService.class).in(Scopes.SINGLETON);
  }
}
