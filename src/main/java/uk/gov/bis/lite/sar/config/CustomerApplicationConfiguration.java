package uk.gov.bis.lite.sar.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerApplicationConfiguration extends Configuration {

  @NotEmpty
  @JsonProperty
  private String soapLiteSarUrl;

  @NotEmpty
  @JsonProperty
  private String soapSiteSarUrl;

  @NotEmpty
  @JsonProperty
  private String soapCompanyUrl;

  @NotEmpty
  @JsonProperty
  private String soapSiteUrl;

  @NotEmpty
  @JsonProperty
  private String soapUserName;

  @NotEmpty
  @JsonProperty
  private String soapPassword;

  public String getSoapCompanyUrl() {
    return soapCompanyUrl;
  }

  public String getSoapSiteUrl() {
    return soapSiteUrl;
  }

  public String getSoapUserName() {
    return soapUserName;
  }

  public String getSoapPassword() {
    return soapPassword;
  }

  public String getSoapLiteSarUrl() {
    return soapLiteSarUrl;
  }

  public String getSoapSiteSarUrl() {
    return soapSiteSarUrl;
  }
}
