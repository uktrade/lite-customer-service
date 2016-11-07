package uk.gov.bis.lite.sar;

import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.sar.config.guice.GuiceModule;
import uk.gov.bis.lite.sar.resource.CustomerCreateResource;
import uk.gov.bis.lite.sar.resource.CustomerResource;
import uk.gov.bis.lite.sar.resource.SiteCreateResource;
import uk.gov.bis.lite.sar.resource.SiteResource;
import uk.gov.bis.lite.sar.resource.UserResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {

  public static void main(String[] args) throws Exception {
    new CustomerApplication().run(args);
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {
    environment.jersey().register(SpireClientException.ServiceExceptionMapper.class);
  }

  @Override
  public void initialize(Bootstrap<CustomerApplicationConfiguration> bootstrap) {
    GuiceBundle<CustomerApplicationConfiguration> guiceBundle =
        GuiceBundle.<CustomerApplicationConfiguration>builder()
            .modules(new GuiceModule())
            .installers(ResourceInstaller.class)
            .extensions(CustomerCreateResource.class, SiteCreateResource.class, UserResource.class,
                CustomerResource.class, SiteResource.class)
            .build(Stage.PRODUCTION);

    bootstrap.addBundle(guiceBundle);
  }
}
