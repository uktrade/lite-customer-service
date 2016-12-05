package uk.gov.bis.lite.customer.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.setup.Environment;
import uk.gov.bis.lite.common.spire.client.SpireClientConfig;
import uk.gov.bis.lite.common.spire.client.SpireRequestConfig;
import uk.gov.bis.lite.common.spire.client.parser.ReferenceParser;
import uk.gov.bis.lite.customer.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.service.CustomerServiceImpl;
import uk.gov.bis.lite.customer.service.SiteService;
import uk.gov.bis.lite.customer.service.SiteServiceImpl;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;
import uk.gov.bis.lite.customer.spire.SiteErrorHandler;
import uk.gov.bis.lite.customer.spire.SpireCompanyClient;
import uk.gov.bis.lite.customer.spire.SpireReferenceClient;
import uk.gov.bis.lite.customer.spire.SpireSiteClient;
import uk.gov.bis.lite.customer.spire.SpireUserDetailClient;
import uk.gov.bis.lite.customer.spire.parsers.CompanyParser;
import uk.gov.bis.lite.customer.spire.parsers.SiteParser;
import uk.gov.bis.lite.customer.spire.parsers.UserDetailParser;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  @Named("SpireCreateLiteSarClient")
  SpireReferenceClient provideCreateLiteSar(Environment env, CustomerApplicationConfiguration config) {
    return new SpireReferenceClient(
        new ReferenceParser("SAR_REF"),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_CREATE_LITE_SAR", "SAR_DETAILS", false));
  }

  @Provides
  @Singleton
  @Named("SpireCreateSiteForSarClient")
  SpireReferenceClient provideCreateSiteForSar(Environment env, CustomerApplicationConfiguration config) {
    return new SpireReferenceClient(new ReferenceParser("SITE_REF"),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_CREATE_SITE_FOR_SAR", "SITE_DETAILS", false),
        new SiteErrorHandler());
  }

  @Provides
  @Singleton
  @Named("SpireEditUserRolesClient")
  SpireReferenceClient provideEditUserRoles(Environment env, CustomerApplicationConfiguration config) {
    return new SpireReferenceClient(new ReferenceParser("RESULT"),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_EDIT_USER_ROLES", "USER_DETAILS", false));
  }

  @Provides
  @Singleton
  SpireCompanyClient provideCompany(Environment env, CustomerApplicationConfiguration config) {
    return new SpireCompanyClient(new CompanyParser(),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_COMPANIES", "getCompanies", true));
  }

  @Provides
  @Singleton
  SpireSiteClient provideCompanySite(Environment env, CustomerApplicationConfiguration config) {
    return new SpireSiteClient(new SiteParser(),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_COMPANY_SITES", "getSites", true));
  }

  @Provides
  @Singleton
  SpireUserDetailClient provideUserDetail(Environment env, CustomerApplicationConfiguration config) {
    return new SpireUserDetailClient(new UserDetailParser(),
        new SpireClientConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl()),
        new SpireRequestConfig("SPIRE_SAR_USER_DETAILS", "getDetails", true));
  }

  @Override
  protected void configure() {
    bind(CustomerService.class).to(CustomerServiceImpl.class);
    bind(SiteService.class).to(SiteServiceImpl.class);
    bind(UserService.class).to(UserServiceImpl.class);
  }

}
