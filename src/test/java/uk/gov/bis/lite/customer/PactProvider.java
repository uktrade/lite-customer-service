package uk.gov.bis.lite.customer;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

@RunWith(PactRunner.class)
@Provider("lite-customer-service")
@PactFolder("//Users//Tomacpro//Projects//GitHub//lite-ogel-registration//target//pacts")
public class PactProvider {

  @ClassRule
  public static final DropwizardAppRule<CustomerApplicationConfiguration> RULE =
    new DropwizardAppRule<>(TestCustomerApplication.class, resourceFilePath("service-test.yaml"));

  @TestTarget // Annotation denotes Target that will be used for tests
  public final Target target = new HttpTarget(RULE.getLocalPort()); // Out-of-the-box implementation of Target (for more information take a look at Test Target section)


}

