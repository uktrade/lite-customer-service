package uk.gov.bis.lite.sar.resource;

import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import uk.gov.bis.lite.sar.mocks.SiteServiceMock;
import uk.gov.bis.lite.sar.util.Util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SiteResourcesTest {

  private final int OK = Response.Status.OK.getStatusCode();

  // SiteServiceMock setup
  private static final int MOCK_SITES_NUMBER = 1;
  private static final String MOCK_SITE_ID = "id1";
  private static final SiteServiceMock mockService = new SiteServiceMock(MOCK_SITE_ID, MOCK_SITES_NUMBER);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new SiteResource(mockService))
      .addResource(new SiteCreateResource(mockService)).build();

  @Test
  public void createSite() {
    Response response = request("/create-site", MediaType.APPLICATION_JSON)
        .post(Entity.entity(Util.getSiteItemJson(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getResponseString(response)).isEqualTo(MOCK_SITE_ID);
  }

  @Test
  public void userSites() {
    Response response = request("/user-sites/customer/customerId/user/userId").get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getCustomersSize(response)).isEqualTo(MOCK_SITES_NUMBER);
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

