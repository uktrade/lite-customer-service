package uk.gov.bis.lite.customer;

import com.codahale.metrics.servlets.AdminServlet;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import io.dropwizard.Application;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller;
import uk.gov.bis.lite.common.auth.admin.AdminConstraintSecurityHandler;
import uk.gov.bis.lite.common.auth.basic.SimpleAuthenticator;
import uk.gov.bis.lite.common.auth.basic.SimpleAuthorizer;
import uk.gov.bis.lite.common.auth.basic.User;
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

    // Authorization and authentication handlers
    SimpleAuthenticator simpleAuthenticator = new SimpleAuthenticator(configuration.getAdminLogin(),
        configuration.getAdminPassword(),
        configuration.getServiceLogin(),
        configuration.getServicePassword());

    BasicCredentialAuthFilter<User> userBasicCredentialAuthFilter = new BasicCredentialAuthFilter.Builder<User>()
        .setAuthenticator(simpleAuthenticator)
        .setAuthorizer(new SimpleAuthorizer())
        .setRealm("User Service Authentication")
        .buildAuthFilter();

    Injector injector = guiceBundle.getInjector();

    environment.jersey().register(ContainerCorrelationIdFilter.class);

    String jwtSharedSecret = injector.getInstance(LiteJwtConfig.class).getKey();

    JwtAuthFilter<LiteJwtUser> liteJwtUserJwtAuthFilter = LiteJwtAuthFilterHelper.buildAuthFilter(jwtSharedSecret);

    PolymorphicAuthDynamicFeature authFeature = new PolymorphicAuthDynamicFeature<>(ImmutableMap.of(
        LiteJwtUser.class, liteJwtUserJwtAuthFilter, User.class, userBasicCredentialAuthFilter));
    environment.jersey().register(authFeature);

    AbstractBinder authBinder = new PolymorphicAuthValueFactoryProvider.Binder<>(ImmutableSet.of(LiteJwtUser.class, User.class));
    environment.jersey().register(authBinder);

    environment.jersey().register(RolesAllowedDynamicFeature.class);

    environment.admin().addServlet("admin", new AdminServlet()).addMapping("/admin");
    environment.admin().setSecurityHandler(new AdminConstraintSecurityHandler(configuration.getServiceLogin(), configuration.getServicePassword()));
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
