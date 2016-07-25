package uk.gov.bis.lite.sar;

import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.sar.config.guice.GuiceModule;
import uk.gov.bis.lite.sar.resource.CustomerResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApplication.class);
  private GuiceBundle<CustomerApplicationConfiguration> guiceBundle;

  public static void main(String[] args) throws Exception {
    new CustomerApplication().run(args);
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {

  }

  @Override
  public void initialize(Bootstrap<CustomerApplicationConfiguration> bootstrap) {
    guiceBundle = GuiceBundle.<CustomerApplicationConfiguration>builder()
        .modules(new GuiceModule())
        .installers(ResourceInstaller.class)
        .extensions(CustomerResource.class)
        .build(Stage.PRODUCTION);

    bootstrap.addBundle(guiceBundle);
  }
}
