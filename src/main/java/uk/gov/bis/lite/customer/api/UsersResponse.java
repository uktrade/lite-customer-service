package uk.gov.bis.lite.customer.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import uk.gov.bis.lite.customer.api.view.UserView;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersResponse {

  private List<UserView> administrators;

  public UsersResponse() {}

  public UsersResponse(List<UserView> administrators) {
    this.administrators = administrators;
  }

  public List<UserView> getAdministrators() {
    return administrators;
  }

  public void setAdministrators(List<UserView> administrators) {
    this.administrators = administrators;
  }
}
