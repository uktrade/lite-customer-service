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

@RunWith(PactRunner.class)
@Provider("lite-customer-service")
//@PactFolder("//Users//Tomacpro//Projects//GitHub//lite-ogel-registration//target//pacts")
//@PactFolder("/Users/dan/bit/lite-permissions-service/target/pacts")
@PactBroker(host = "pact-broker.mgmt.licensing.service.trade.gov.uk.test", port = "80")
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

}

