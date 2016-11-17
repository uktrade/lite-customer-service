package uk.gov.bis.lite.customer;

import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.common.jersey.filter.ContainerCorrelationIdFilter;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.customer.config.guice.GuiceModule;
import uk.gov.bis.lite.customer.resource.CustomerCreateResource;
import uk.gov.bis.lite.customer.resource.CustomerResource;
import uk.gov.bis.lite.customer.resource.SiteCreateResource;
import uk.gov.bis.lite.customer.resource.SiteResource;
import uk.gov.bis.lite.customer.resource.UserResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {

  public static void main(String[] args) throws Exception {
    new CustomerApplication().run(args);
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {
    environment.jersey().register(SpireClientException.ServiceExceptionMapper.class);
    environment.jersey().register(ContainerCorrelationIdFilter.class);
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
