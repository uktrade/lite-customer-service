package uk.gov.bis.lite.customer;

import uk.gov.bis.lite.customer.config.guice.GuiceModule;
import uk.gov.bis.lite.customer.mocks.permissions.MockCustomerService;
import uk.gov.bis.lite.customer.mocks.permissions.MockSiteService;
import uk.gov.bis.lite.customer.mocks.permissions.MockUserService;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.UserService;

public class GuiceTestPermissionsPactModule extends GuiceModule {

  @Override
  protected void configure() {
    bind(CustomerService.class).to(MockCustomerService.class);
    bind(SiteService.class).to(MockSiteService.class);
    bind(UserService.class).to(MockUserService.class);
  }
}
