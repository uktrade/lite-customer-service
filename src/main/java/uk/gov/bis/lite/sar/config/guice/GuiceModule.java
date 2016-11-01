package uk.gov.bis.lite.sar.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.setup.Environment;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.sar.spire.SpireCompanyClient;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.spire.SpireSiteClient;
import uk.gov.bis.lite.sar.spire.parsers.CompanyParser;
import uk.gov.bis.lite.sar.spire.parsers.SiteParser;
import uk.gov.bis.lite.spire.client.parser.ReferenceParser;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  @Named("SpireCreateLiteSarClient")
  SpireReferenceClient provideCreateLiteSar(Environment env, CustomerApplicationConfiguration config) {
    SpireReferenceClient client = new SpireReferenceClient(new ReferenceParser("SAR_REF"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_CREATE_LITE_SAR", "SAR_DETAILS", false);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireCreateSiteForSarClient")
  SpireReferenceClient provideCreateSiteForSar(Environment env, CustomerApplicationConfiguration config) {
    SpireReferenceClient client = new SpireReferenceClient(new ReferenceParser("SITE_REF"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_CREATE_SITE_FOR_SAR", "SITE_DETAILS", false);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireEditUserRolesClient")
  SpireReferenceClient provideEditUserRoles(Environment env, CustomerApplicationConfiguration config) {
    SpireReferenceClient client = new SpireReferenceClient(new ReferenceParser("RESULT"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_EDIT_USER_ROLES", "USER_DETAILS", false);
    return client;
  }


  @Provides
  @Singleton
  SpireCompanyClient provideCompany(Environment env, CustomerApplicationConfiguration config) {
    SpireCompanyClient client = new SpireCompanyClient(new CompanyParser());
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_COMPANIES", "getCompanies", true);
    return client;
  }

  @Provides
  @Singleton
  SpireSiteClient provideCompanySite(Environment env, CustomerApplicationConfiguration config) {
    SpireSiteClient client = new SpireSiteClient(new SiteParser());
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_COMPANY_SITES", "getSites", true);
    return client;
  }

  @Override
  protected void configure() {
  }

}
