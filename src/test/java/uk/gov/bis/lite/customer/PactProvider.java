package uk.gov.bis.lite.customer;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.customer.mocks.UserServiceMock;
import uk.gov.bis.lite.customer.mocks.permissions.MockCustomerService;
import uk.gov.bis.lite.customer.mocks.permissions.MockSiteService;
import uk.gov.bis.lite.customer.mocks.permissions.MockUserService;

@RunWith(PactRunner.class)
@Provider("lite-customer-service")
@PactBroker(host = "pact-broker.mgmt.licensing.service.trade.gov.uk.test", port = "80")
//@VerificationReports({"console", "markdown"})
public class PactProvider {

  @ClassRule
  public static final DropwizardAppRule<CustomerApplicationConfiguration> RULE =
    new DropwizardAppRule<>(TestCustomerApplication.class, resourceFilePath("service-test.yaml"));

  @TestTarget
  public final Target target = new HttpTarget(RULE.getLocalPort());


  @State("existing user role")
  public void toExistingUserRoleState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(UserServiceMock.class).setUpExistingUserRole();
  }

  @State("failed user role")
  public void toFailedUserRoleState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(UserServiceMock.class).setUpFailedUserRole();
  }

  @State("create customer success")
  public void createCustomerSuccessState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setFailCreateCustomer(false);
  }

  @State("create customer fail")
  public void createCustomerFailState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setFailCreateCustomer(true);
  }

  @State("customer by company number success")
  public void customerByCompanyNumberSuccessState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setFailCustomersByCustomerNumber(false);
  }

  @State("customer by company number fail")
  public void customerByCompanyNumberFailState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setFailCustomersByCustomerNumber(true);
  }

  @State("create site success")
  public void createSiteSuccessState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setFailCreateSite(false);
  }

  @State("create site fail")
  public void createSiteFailState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setFailCreateSite(true);
  }

  @State("update user role success")
  public void updateUserRoleSuccessState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setFailUpdateUserRole(false);
  }

  @State("update user role fail")
  public void updateUserRoleFailState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setFailUpdateUserRole(true);
  }

}

