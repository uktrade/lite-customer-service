package uk.gov.bis.lite.sar.config.guice;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;

import javax.inject.Named;

public class GuiceModule extends AbstractModule {

  @Provides
  @Named("soapCompanyUrl")
  public String provideSpireCompanyUrl(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapCompanyUrl();
  }

  @Provides
  @Named("soapSiteUrl")
  public String provideSpireSiteUrl(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapSiteUrl();
  }

  @Provides
  @Named("soapUserName")
  public String provideSpireSiteClientUserName(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapUserName();
  }

  @Provides
  @Named("soapPassword")
  public String provideSpireSiteClientPassword(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapPassword();
  }

  @Override
  protected void configure() {
  }

}
