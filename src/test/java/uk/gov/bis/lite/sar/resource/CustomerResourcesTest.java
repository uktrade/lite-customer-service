package uk.gov.bis.lite.sar.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import uk.gov.bis.lite.sar.mocks.CustomerServiceMock;
import uk.gov.bis.lite.sar.util.Util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Testing Resources:
 * CustomerResource
 * CustomerCreateResource
 */
public class CustomerResourcesTest {

  private final int OK = Response.Status.OK.getStatusCode();

  // CustomerServiceMock setup
  private static final int MOCK_CUSTOMERS_NUMBER = 1;
  private static final String MOCK_CUSTOMER_ID = "id1";
  private static final CustomerServiceMock mockService = new CustomerServiceMock(MOCK_CUSTOMER_ID, MOCK_CUSTOMERS_NUMBER);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new CustomerResource(mockService))
      .addResource(new CustomerCreateResource(mockService)).build();

  @Test
  public void createCustomer() {
    Response response = request("/create-customer", MediaType.APPLICATION_JSON)
        .post(Entity.entity(Util.getCustomerItemJson(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getResponseString(response)).isEqualTo(MOCK_CUSTOMER_ID);
  }

  @Test
  public void customers() {
    Response response = request("/customers/1").get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getCustomersSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
  }

  @Test
  public void userCustomers() {
    Response response = request("/user-customers/user/1").get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getCustomersSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
  }

  @Test
  public void searchCustomers() {

    String url = "/search-customers/org-info";

    // Missing required param
    Response response = request(url).get();
    assertEquals(400, response.getStatus());

    // With postcode param
    response = target(url).queryParam("postcode", "postcode").request().get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getCustomersSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With eori param
    response = target(url).queryParam("eori", "eori").request().get();
    assertEquals(400, response.getStatus());

    // With postcode and eori param
    response = target(url).queryParam("postcode", "postcode")
        .queryParam("eori", "eori").request().get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(Util.getCustomersSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
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

