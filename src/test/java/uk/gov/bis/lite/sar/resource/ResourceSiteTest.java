package uk.gov.bis.lite.sar.resource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceSiteTest extends SpireResourceTest {

  @Test
  public void createSite() {
    Response response = request("/create-site", MediaType.APPLICATION_JSON)
        .post(Entity.entity(getSiteItemJson(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseString(response)).isEqualTo(MOCK_SITE_ID);
  }

  @Test
  public void userSites() {
    Response response = request("/user-sites/customer/customerId/user/userId").get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomersSize(response)).isEqualTo(MOCK_SITES_NUMBER);
  }

}

