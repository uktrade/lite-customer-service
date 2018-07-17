package uk.gov.bis.lite.customer;

import com.google.inject.Module;
import uk.gov.bis.lite.customer.config.GuiceModule;

public class TestCustomerApplication extends CustomerApplication {

  public TestCustomerApplication() {
    super(new Module[]{new GuiceModule(), new TestServiceModule()});
  }

}
