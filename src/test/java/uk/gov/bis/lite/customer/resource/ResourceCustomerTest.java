package uk.gov.bis.lite.customer.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static uk.gov.bis.lite.customer.JwtUtil.generateToken;

import org.junit.Test;
import uk.gov.bis.lite.customer.api.view.CustomerView;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Testing Resources:
 * CustomerResource
 * CustomerCreateResource
 */
public class ResourceCustomerTest extends SpireResourceTest {

  @Test
  public void createCustomer() {
    Response response = request("/create-customer", MediaType.APPLICATION_JSON)
        .post(Entity.entity(getCustomerParam(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseCustomerView(response).getCustomerId()).isEqualTo(MOCK_CUSTOMER_ID);
  }

  @Test
  public void customers() {
    Response response = request("/customers/EXISTING_CUSTOMER")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseCustomerView(response).getCustomerId()).isEqualTo(MOCK_CUSTOMER_ID);

    // Without valid auth header
    response = request("/customers/EXISTING_CUSTOMER").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

  @Test
  public void userCustomers() {
    Response response = request("/user-customers/user/1")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "1"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With mismatched user id (1) to jwt subject (123456)
    response = request("/user-customers/user/1")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);

    // Without valid auth header
    response = request("/user-customers/user/1").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

  @Test
  public void searchCustomers() {

    String url = "/search-customers/org-info";

    // Missing required param
    Response response = request(url)
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertEquals(400, response.getStatus());

    // With postcode param
    response = target(url).queryParam("postcode", "postcode").request()
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With eori param
    response = target(url).queryParam("eori", "eori").request()
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertEquals(400, response.getStatus());

    // With postcode and eori param
    response = target(url).queryParam("postcode", "postcode")
        .queryParam("eori", "eori").request()
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // Without valid auth header
    response = request(url).get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

  @Test
  public void searchCustomersByCompanyNumber() {
    Response response = request("/search-customers/registered-number/1")
        .header("Authorization", "Bearer " + generateToken(JWT_SHARED_SECRET, "123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    CustomerView customer = getCustomerResponse(response);
    assertThat(customer.getCustomerId()).isEqualTo(MOCK_CUSTOMERS_SAR_REF_TAG + "1");

    // Without valid auth header
    response = request("/search-customers/registered-number/1").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

}

