package uk.gov.bis.lite.sar.config.guice;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;

import javax.inject.Named;

public class GuiceModule extends AbstractModule {

  @Provides
  @Named("soapUrl")
  public String provideSpireOgelUrl(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapUrl();
  }

  @Provides
  @Named("soapUserName")
  public String provideSpireOgelClientUserName(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapUserName();
  }

  @Provides
  @Named("soapPassword")
  public String provideSpireOgelClientPassword(CustomerApplicationConfiguration configuration) {
    return configuration.getSoapPassword();
  }

  @Override
  protected void configure() {
  }

}
