package uk.gov.bis.lite.sar.resource;


import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import uk.gov.bis.lite.sar.mocks.UserServiceMock;
import uk.gov.bis.lite.sar.util.Util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserResourcesTest {

  private final int OK = Response.Status.OK.getStatusCode();

  // UserServiceMock setup
  private static final int MOCK_USERS_USER_DETAIL_NUMBER = 3;
  private static final String MOCK_COMPLETE = "COMPLETE";
  private static final UserServiceMock mockService = new UserServiceMock(MOCK_COMPLETE, MOCK_USERS_USER_DETAIL_NUMBER);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new UserResource(mockService)).build();

  @Test
  public void userRole() {
    Response response = request("/user-roles/user/userId/site/siteRef", MediaType.APPLICATION_JSON)
        .post(Entity.entity(Util.getUserRoleItemJson(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getResponseString(response)).isEqualTo(MOCK_COMPLETE);
  }


  @Test
  public void customerAdmins() {
    Response response = request("/customer-admins/customerId").get();
    assertThat(status(response)).isEqualTo(OK);
    // UserDetail requires fields otherwise Response readEntity produces an error?
    // Create Users/UserDetail versions just for testing?
    // TODO fix
    //assertThat(Util.getUsersUserDetailsSize(response)).isEqualTo(MOCK_USERS_USER_DETAIL_NUMBER);
  }


  /**
   * private methods
   */
  private Invocation.Builder request(String url, String mediaType) {
    return target(url).request(mediaType);
  }

  private Invocation.Builder request(String url) {
    return target(url).request();
  }

  private WebTarget target(String url) {
    return resources.client().target(url);
  }

  private int status(Response response) {
    return response.getStatus();
  }
}

