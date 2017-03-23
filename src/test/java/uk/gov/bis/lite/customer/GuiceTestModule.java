package uk.gov.bis.lite.customer;

import uk.gov.bis.lite.customer.config.guice.GuiceModule;
import uk.gov.bis.lite.customer.mocks.CustomerServiceMock;
import uk.gov.bis.lite.customer.mocks.SiteServiceMock;
import uk.gov.bis.lite.customer.mocks.UserServiceMock;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.UserService;

public class GuiceTestModule extends GuiceModule {

  @Override
  protected void configure() {
    bind(CustomerService.class).to(CustomerServiceMock.class);
    bind(SiteService.class).to(SiteServiceMock.class);
    bind(UserService.class).to(UserServiceMock.class);
  }
}