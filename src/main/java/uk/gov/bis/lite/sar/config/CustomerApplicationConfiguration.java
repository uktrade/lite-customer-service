package uk.gov.bis.lite.sar.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerApplicationConfiguration extends Configuration {


  @NotEmpty
  @JsonProperty
  private String spireClientUserName;

  @NotEmpty
  @JsonProperty
  private String spireClientPassword;

  @NotEmpty
  @JsonProperty
  private String spireClientUrl;

  @NotEmpty
  @JsonProperty
  private String spireServiceUserName;

  @NotEmpty
  @JsonProperty
  private String spireServicePassword;

  @NotEmpty
  @JsonProperty
  private String spireServiceUrl;

  @NotEmpty
  @JsonProperty
  private String spireServiceActiveEndpoints;

  @NotEmpty
  @JsonProperty
  private String createLiteSarUrl;

  @NotEmpty
  @JsonProperty
  private String createSiteForSarUrl;

  @NotEmpty
  @JsonProperty
  private String editUserRolesUrl;

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

  public String getCreateLiteSarUrl() {
    return createLiteSarUrl;
  }

  public String getCreateSiteForSarUrl() {
    return createSiteForSarUrl;
  }

  public String getEditUserRolesUrl() {
    return editUserRolesUrl;
  }

  public String getSpireServiceUserName() {
    return spireServiceUserName;
  }

  public void setSpireServiceUserName(String spireServiceUserName) {
    this.spireServiceUserName = spireServiceUserName;
  }

  public String getSpireServicePassword() {
    return spireServicePassword;
  }

  public void setSpireServicePassword(String spireServicePassword) {
    this.spireServicePassword = spireServicePassword;
  }

  public String getSpireServiceUrl() {
    return spireServiceUrl;
  }

  public void setSpireServiceUrl(String spireServiceUrl) {
    this.spireServiceUrl = spireServiceUrl;
  }

  public String getSpireServiceActiveEndpoints() {
    return spireServiceActiveEndpoints;
  }

  public void setSpireServiceActiveEndpoints(String spireServiceActiveEndpoints) {
    this.spireServiceActiveEndpoints = spireServiceActiveEndpoints;
  }

  public String getSpireClientUserName() {
    return spireClientUserName;
  }

  public String getSpireClientPassword() {
    return spireClientPassword;
  }

  public String getSpireClientUrl() {
    return spireClientUrl;
  }
}
