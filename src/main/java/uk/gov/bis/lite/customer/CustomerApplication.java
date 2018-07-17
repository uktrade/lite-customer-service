package uk.gov.bis.lite.customer;

import com.codahale.metrics.servlets.AdminServlet;
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
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.common.auth.admin.AdminConstraintSecurityHandler;
import uk.gov.bis.lite.common.jersey.filter.ContainerCorrelationIdFilter;
import uk.gov.bis.lite.common.jwt.LiteJwtAuthFilterHelper;
import uk.gov.bis.lite.common.jwt.LiteJwtConfig;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.common.paas.db.CloudFoundryEnvironmentSubstitutor;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.customer.config.GuiceModule;
import uk.gov.bis.lite.customer.config.RedisServiceModule;
import uk.gov.bis.lite.customer.resource.CustomerCreateResource;
import uk.gov.bis.lite.customer.resource.CustomerResource;
import uk.gov.bis.lite.customer.resource.CustomerSiteResource;
import uk.gov.bis.lite.customer.resource.SiteResource;
import uk.gov.bis.lite.customer.resource.UserResource;

public class CustomerApplication extends Application<CustomerApplicationConfiguration> {

  private GuiceBundle<CustomerApplicationConfiguration> guiceBundle;
  private final Module[] modules;

  public static void main(String[] args) throws Exception {
    new CustomerApplication(new Module[]{new GuiceModule(), new RedisServiceModule()}).run(args);
  }

  public CustomerApplication(Module[] modules) {
    super();
    this.modules = modules;
  }

  @Override
  public void run(CustomerApplicationConfiguration configuration, Environment environment) {
    Injector injector = guiceBundle.getInjector();

    environment.jersey().register(ContainerCorrelationIdFilter.class);

    String jwtSharedSecret = injector.getInstance(LiteJwtConfig.class).getKey();

    JwtAuthFilter<LiteJwtUser> liteJwtUserJwtAuthFilter = LiteJwtAuthFilterHelper.buildAuthFilter(jwtSharedSecret);

    environment.jersey().register(new AuthDynamicFeature(liteJwtUserJwtAuthFilter));
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(LiteJwtUser.class));

    environment.admin().addServlet("admin", new AdminServlet()).addMapping("/admin");
    environment.admin().setSecurityHandler(new AdminConstraintSecurityHandler(configuration.getLogin(), configuration.getPassword()));
  }

  @Override
  public void initialize(Bootstrap<CustomerApplicationConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        new ResourceConfigurationSourceProvider(), new CloudFoundryEnvironmentSubstitutor()));

    guiceBundle = GuiceBundle.<CustomerApplicationConfiguration>builder()
        .modules(modules)
        .installers(ResourceInstaller.class)
        .extensions(CustomerCreateResource.class, CustomerSiteResource.class, UserResource.class,
            CustomerResource.class, SiteResource.class)
        .build(Stage.PRODUCTION);

    bootstrap.addBundle(guiceBundle);
  }

}
