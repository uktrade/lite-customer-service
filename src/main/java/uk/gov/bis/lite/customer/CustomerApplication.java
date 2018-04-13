package uk.gov.bis.lite.customer;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.common.jersey.filter.ContainerCorrelationIdFilter;
import uk.gov.bis.lite.common.jwt.LiteJwtAuthFilterHelper;
import uk.gov.bis.lite.common.jwt.LiteJwtConfig;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.common.paas.db.CloudFoundryEnvironmentSubstitutor;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.customer.config.guice.GuiceModule;
import uk.gov.bis.lite.customer.resource.CustomerCreateResource;
import uk.gov.bis.lite.customer.resource.CustomerResource;
import uk.gov.bis.lite.customer.resource.CustomerSiteResource;
import uk.gov.bis.lite.customer.resource.SiteResource;
import uk.gov.bis.lite.customer.resource.UserResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {

  private GuiceBundle<CustomerApplicationConfiguration> guiceBundle;
  private final Module module;

  public static void main(String[] args) throws Exception {
    new CustomerApplication(new GuiceModule()).run(args);
  }

  public GuiceBundle<CustomerApplicationConfiguration> getGuiceBundle() {
    return guiceBundle;
  }

  public CustomerApplication(Module module) {
    super();
    this.module = module;
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {
    Injector injector = InjectorLookup.getInjector(this).get();

    environment.jersey().register(ContainerCorrelationIdFilter.class);

    String jwtSharedSecret = injector.getInstance(LiteJwtConfig.class).getKey();

    JwtAuthFilter<LiteJwtUser> liteJwtUserJwtAuthFilter = LiteJwtAuthFilterHelper.buildAuthFilter(jwtSharedSecret);

    environment.jersey().register(new AuthDynamicFeature(liteJwtUserJwtAuthFilter));
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(LiteJwtUser.class));
  }

  @Override
  public void initialize(Bootstrap<CustomerApplicationConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        new ResourceConfigurationSourceProvider(), new CloudFoundryEnvironmentSubstitutor()));

    GuiceBundle<CustomerApplicationConfiguration> guiceBundle =
        GuiceBundle.<CustomerApplicationConfiguration>builder()
            .modules(module)
            .installers(ResourceInstaller.class)
            .extensions(CustomerCreateResource.class, CustomerSiteResource.class, UserResource.class,
                CustomerResource.class, SiteResource.class)
            .build(Stage.PRODUCTION);

    bootstrap.addBundle(guiceBundle);
  }

}
