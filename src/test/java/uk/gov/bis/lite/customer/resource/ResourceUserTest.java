package uk.gov.bis.lite.customer.resource;


import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.bis.lite.customer.JwtUtil.generateToken;

import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceUserTest extends SpireResourceTest {

  @Test
  public void userRole() {
    Response response = request("/user-roles/user/EXISTING_USER/site/EXISTING_SITE", MediaType.APPLICATION_JSON)
        .post(Entity.entity(getUserRoleParam(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
  }


  @Test
  public void customerAdmins() {
    Response response = request("/customer-admins/EXISTING_CUSTOMER")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getUsersUserDetailsSize(response)).isEqualTo(MOCK_USERS_USER_DETAIL_NUMBER);

    // Without valid auth header
    response = request("/customer-admins/EXISTING_CUSTOMER").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

}

