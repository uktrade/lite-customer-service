package uk.gov.bis.lite.sar.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.setup.Environment;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.spireclient.SpireClientService;

import javax.inject.Named;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  SpireClientService provideSpireService(Environment environment, CustomerApplicationConfiguration config) {
    SpireClientService service = new SpireClientService();
    service.init(config.getSpireServiceUserName(), config.getSpireServicePassword(),
        config.getSpireServiceUrl(), config.getSpireServiceActiveEndpoints());
    return service;
  }

  @Provides
  @Named("createLiteSarUrl")
  public String provideSpireCreateLiteSarUrl(CustomerApplicationConfiguration config) {
    return config.getCreateLiteSarUrl();
  }

  @Provides
  @Named("createSiteForSarUrl")
  public String provideSpireCreateSiteForSarUrl(CustomerApplicationConfiguration config) {
    return config.getCreateSiteForSarUrl();
  }

  @Provides
  @Named("editUserRolesUrl")
  public String provideSpireEditUserRolesUrl(CustomerApplicationConfiguration config) {
    return config.getEditUserRolesUrl();
  }

  @Provides
  @Named("soapCompanyUrl")
  public String provideSpireCompanyUrl(CustomerApplicationConfiguration config) {
    return config.getSoapCompanyUrl();
  }

  @Provides
  @Named("soapSiteUrl")
  public String provideSpireSiteUrl(CustomerApplicationConfiguration config) {
    return config.getSoapSiteUrl();
  }

  @Provides
  @Named("soapUserName")
  public String provideSpireSiteClientUserName(CustomerApplicationConfiguration config) {
    return config.getSoapUserName();
  }

  @Provides
  @Named("soapPassword")
  public String provideSpireSiteClientPassword(CustomerApplicationConfiguration config) {
    return config.getSoapPassword();
  }

  @Override
  protected void configure() {
  }

}
