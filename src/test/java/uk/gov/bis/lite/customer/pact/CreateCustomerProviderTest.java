package uk.gov.bis.lite.customer.pact;


import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import uk.gov.bis.lite.customer.TestCustomerApplication;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;

import java.util.Base64;

@RunWith(PactRunner.class)
@Provider("customerServiceCreateCustomerProvider")
@PactUrl(urls = {"file://Users/dan/bit/lite-permissions-service/target/pacts/permissionsServiceCreateCustomerConsumer-customerServiceCreateCustomerProvider.json"})
public class CreateCustomerProviderTest  {

  @ClassRule
  public static final DropwizardAppRule<CustomerApplicationConfiguration> RULE =
      new DropwizardAppRule<>(TestCustomerApplication.class, resourceFilePath("service-test.yaml"));

  @TestTarget // Annotation denotes Target that will be used for tests
  public final Target target = new HttpTarget(RULE.getLocalPort());

  @BeforeClass
  public static void setUpService() {}

  @Before
  public void before() {
  }

  @State("default") // Method will be run before testing interactions that require "default" or "no-data" state
  public void toDefaultState() {
    // Prepare service before interaction that require "default" state
  }

  @TargetRequestFilter
  public void exampleRequestFilter(HttpRequest request) {
    request.addHeader("Authorization", "Basic " + base64StringOf("admin", "admin"));
  }

  private String base64StringOf(String username, String password) {
    return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
  }

}