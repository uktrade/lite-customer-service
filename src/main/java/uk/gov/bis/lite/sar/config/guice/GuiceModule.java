package uk.gov.bis.lite.sar.config.guice;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;

import javax.inject.Named;

public class GuiceModule extends AbstractModule {

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
