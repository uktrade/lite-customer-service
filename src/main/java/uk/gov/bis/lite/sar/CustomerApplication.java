package uk.gov.bis.lite.sar;

import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.sar.config.guice.GuiceModule;
import uk.gov.bis.lite.sar.exception.CompanyNotFoundException;
import uk.gov.bis.lite.sar.exception.SiteNotFoundException;
import uk.gov.bis.lite.sar.resource.CustomerResource;
import uk.gov.bis.lite.sar.resource.SiteResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {

  public static boolean MOCK = false;

  public static void main(String[] args) throws Exception {
    new CustomerApplication().run(args);
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {
    environment.jersey().register(CompanyNotFoundException.CompanyNotFoundExceptionHandler.class);
    environment.jersey().register(SiteNotFoundException.SiteNotFoundExceptionHandler.class);
  }

  @Override
  public void initialize(Bootstrap<CustomerApplicationConfiguration> bootstrap) {
    GuiceBundle<CustomerApplicationConfiguration> guiceBundle =
        GuiceBundle.<CustomerApplicationConfiguration>builder()
            .modules(new GuiceModule())
            .installers(ResourceInstaller.class)
            .extensions(CustomerResource.class, SiteResource.class)
            .build(Stage.PRODUCTION);

    bootstrap.addBundle(guiceBundle);
  }
}
