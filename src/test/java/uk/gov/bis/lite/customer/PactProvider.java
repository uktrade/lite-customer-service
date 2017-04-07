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

  @State("user role update request is valid")
  public void updateUserRoleSuccessState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setFailUpdateUserRole(false);
  }

  @State("user role update request is invalid")
  public void updateUserRoleFailState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setFailUpdateUserRole(true);
  }

  @State("provided customer exists and has admins")
  public void existingCustomerAdminUsersState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setMissingCustomerAdminUsers(false);
  }

  @State("provided customer has no admins")
  public void missingCustomerAdminUsersState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockUserService.class).setMissingCustomerAdminUsers(true);
  }

  @State("provided customer ID exists")
  public void existingCustomerState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMissingCustomer(false);
  }

  @State("provided customer ID does not exist")
  public void missingCustomerState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMissingCustomer(true);
  }

  @State("provided site ID exists")
  public void existingSiteState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setMissingSite(false);
  }

  @State("provided site ID does not exist")
  public void missingSiteState() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setMissingSite(true);
  }

  @State("provided user is associated with at least 1 customer")
  public void sitesForExistingUser() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMockCustomersForUserIdSearch(true);
  }

  @State("provided user is associated with no customers")
  public void noSitesForExistingUser() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMockCustomersForUserIdSearch(false);
  }

  @State("sites exist for the provided customer and user")
  public void sitesForCustomerUser() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setSitesExistForUserAndCustomer(true);
  }

  @State("no sites exist for the provided customer and user")
  public void noSitesForCustomerUser() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockSiteService.class).setSitesExistForUserAndCustomer(false);
  }

  @State("some customers match search criteria")
  public void customersMatchSearchCriteria() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMockCustomersForDetailSearch(true);
  }

  @State("no customers match search criteria")
  public void noCustomersMatchSearchCriteria() {
    InjectorLookup.getInjector(RULE.getApplication()).get().getInstance(MockCustomerService.class).setMockCustomersForDetailSearch(false);
  }
}

