package uk.gov.bis.lite.customer.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.bis.lite.customer.JwtUtil.generateToken;

import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceSiteTest extends SpireResourceTest {

  @Test
  public void createSite() {
    Response response = request("/customer-sites/1?userId=1", MediaType.APPLICATION_JSON)
        .post(Entity.entity(getSiteParam(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseSiteView(response).getSiteId()).isEqualTo(MOCK_SITE_ID);
  }

  @Test
  public void userSites() {
    Response response = request("/user-sites/customer/1/user/1")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "1"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_SITES_NUMBER);

    // With mismatched user id (1) to jwt subject (123456)
    response = request("/user-sites/customer/1/user/1")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);

    // Without valid auth header
    response = request("/user-sites/customer/1/user/1").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

  @Test
  public void userSite() {
    Response response = request("/sites/EXISTING_SITE")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseSiteView(response).getSiteId()).isEqualTo(MOCK_SITE_ID);

    // Without valid auth header
    response = request("/sites/EXISTING_SITE").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

}

