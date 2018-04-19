package uk.gov.bis.lite.customer;

import com.google.inject.util.Modules;
import uk.gov.bis.lite.customer.config.guice.GuiceModule;

public class TestCustomerApplication extends CustomerApplication {

  public TestCustomerApplication() {
    super(Modules.override(new GuiceModule()).with(new GuiceTestModule()));
  }

}
