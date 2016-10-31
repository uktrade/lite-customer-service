package uk.gov.bis.lite.sar.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.setup.Environment;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.sar.spire.CompanyParser;
import uk.gov.bis.lite.sar.spire.CompanySiteParser;
import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.spire.client.model.SpireCompany;
import uk.gov.bis.lite.spire.client.model.SpireSite;
import uk.gov.bis.lite.spire.client.parser.SingleResponseParser;

import java.util.List;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  @Named("SpireCreateLiteSarClient")
  SpireClient provideCreateLiteSar1SpireClient(Environment env, CustomerApplicationConfiguration config) {
    SpireClient<String> client = new SpireClient<>(new SingleResponseParser("SAR_REF"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_CREATE_LITE_SAR", "SAR_DETAILS", false);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireCreateSiteForSarClient")
  SpireClient provideCreateSiteForSarSpireClient(Environment env, CustomerApplicationConfiguration config) {
    SpireClient<String> client = new SpireClient<>(new SingleResponseParser("SITE_REF"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_CREATE_SITE_FOR_SAR", "SITE_DETAILS", false);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireEditUserRolesClient")
  SpireClient provideEditUserRolesSpireClient(Environment env, CustomerApplicationConfiguration config) {
    SpireClient<String> client = new SpireClient<>(new SingleResponseParser("RESULT"));
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_EDIT_USER_ROLES", "USER_DETAILS", false);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireCompanyClient")
  SpireClient provideCompanyClient(Environment env, CustomerApplicationConfiguration config) {
    SpireClient<List<SpireCompany>> client = new SpireClient<>(new CompanyParser());
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_COMPANIES", "getCompanies", true);
    return client;
  }

  @Provides
  @Singleton
  @Named("SpireCompanySitesClient")
  SpireClient provideCompanySitesSpireClient(Environment env, CustomerApplicationConfiguration config) {
    SpireClient<List<SpireSite>> client = new SpireClient<>(new CompanySiteParser());
    client.setSpireConfig(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    client.setConfig("SPIRE_COMPANY_SITES", "getSites", true);
    return client;
  }

  @Override
  protected void configure() {
  }

}
