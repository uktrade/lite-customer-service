package uk.gov.bis.lite.sar.config.guice;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;

import javax.inject.Named;

public class GuiceModule extends AbstractModule {

  @Provides
  @Named("soapLiteSarUrl")
  public String provideSpireLiteSarUrl(CustomerApplicationConfiguration config) {
    return config.getSoapLiteSarUrl();
  }

  @Provides
  @Named("soapSiteSarUrl")
  public String provideSpireSiteSarUrl(CustomerApplicationConfiguration config) {
    return config.getSoapSiteSarUrl();
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
