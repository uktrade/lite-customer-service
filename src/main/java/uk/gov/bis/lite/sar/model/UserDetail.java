package uk.gov.bis.lite.sar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.gov.bis.lite.sar.spire.model.SpireUserDetail;

@JsonPropertyOrder({"userId", "forename", "surname", "fullName", "emailAddress", "roleName"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetail {

  private SpireUserDetail userDetail;

  public UserDetail(){}

  public UserDetail(SpireUserDetail userDetail) {
    this.userDetail = userDetail;
  }

  @JsonProperty("userId")
  public String userId() {
    return userDetail.getUserId();
  }

  @JsonProperty("forename")
  public String forename() {
    return userDetail.getForename();
  }

  @JsonProperty("surname")
  public String surname() {
    return userDetail.getSurname();
  }

  @JsonProperty("fullName")
  public String fullName() {
    return userDetail.getFullName();
  }

  @JsonProperty("emailAddress")
  public String emailAddress() {
    return userDetail.getEmailAddress();
  }

  @JsonProperty("roleName")
  public String roleName() {
    return userDetail.getRoleName();
  }

}
