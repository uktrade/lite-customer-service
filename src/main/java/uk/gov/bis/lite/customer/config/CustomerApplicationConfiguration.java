package uk.gov.bis.lite.customer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
import uk.gov.bis.lite.common.redis.RedisConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
  private String login;

  @NotEmpty
  @JsonProperty
  private String password;

  @NotNull
  @Valid
  @JsonProperty("redis")
  private RedisConfiguration redisConfiguration;

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

  public String getLogin() {
    return login;
  }

  public String getPassword() {
    return password;
  }

  public RedisConfiguration getRedisConfiguration() {
    return redisConfiguration;
  }

}
