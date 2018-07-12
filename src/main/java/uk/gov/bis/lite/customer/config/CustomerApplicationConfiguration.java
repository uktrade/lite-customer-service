package uk.gov.bis.lite.customer.config;

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
  private String jwtSharedSecret;

  @NotEmpty
  @JsonProperty
  private String adminLogin;

  @NotEmpty
  @JsonProperty
  private String adminPassword;

  @NotEmpty
  @JsonProperty
  private String serviceLogin;

  @NotEmpty
  @JsonProperty
  private String servicePassword;

  @NotEmpty
  @JsonProperty
  private String login;

  @NotEmpty
  @JsonProperty
  private String password;

  public String getSpireClientUserName() {
    return spireClientUserName;
  }

  public String getSpireClientPassword() {
    return spireClientPassword;
  }

  public String getSpireClientUrl() {
    return spireClientUrl;
  }

  public String getJwtSharedSecret() {
    return jwtSharedSecret;
  }

  public String getAdminLogin() {
    return adminLogin;
  }

  public String getAdminPassword() {
    return adminPassword;
  }

  public String getServiceLogin() {
    return serviceLogin;
  }

  public String getServicePassword() {
    return servicePassword;
  }

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }
}
