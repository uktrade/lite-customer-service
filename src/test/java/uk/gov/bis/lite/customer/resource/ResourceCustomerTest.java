package uk.gov.bis.lite.customer.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import uk.gov.bis.lite.customer.api.view.CustomerView;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Testing Resources:
 * CustomerResource
 * CustomerCreateResource
 * CustomerSiteResource
 */
public class ResourceCustomerTest extends SpireResourceTest {

  @Test
  public void createCustomer() {
    Response response = request("/create-customer", MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .post(Entity.entity(getCustomerParam(), MediaType.APPLICATION_JSON));
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getResponseCustomerView(response).getCustomerId()).isEqualTo(MOCK_CUSTOMER_ID);
  }

  @Test
  public void customers() {
    Response response = request("/customers/EXISTING_CUSTOMER")
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
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
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("1"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With mismatched user id (1) to jwt subject (123456)
    response = request("/user-customers/user/1")
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
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
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertEquals(400, response.getStatus());

    // With postcode param
    response = target(url).queryParam("postcode", "postcode").request()
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With eori param
    response = target(url).queryParam("eori", "eori").request()
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertEquals(400, response.getStatus());

    // With postcode and eori param
    response = target(url).queryParam("postcode", "postcode")
        .queryParam("eori", "eori").request()
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
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
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    CustomerView customer = getCustomerResponse(response);
    assertThat(customer.getCustomerId()).isEqualTo(MOCK_CUSTOMERS_SAR_REF_TAG + "1");
  }

  @Test
  public void searchCustomersByNameOrCompanyNumber() {
    Response response = request("/search-customers?term=1")
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_SITES_NUMBER);

    // Missing required param
    response = request("/search-customers")
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("123456"))
        .get();
    assertEquals(400, response.getStatus());
  }

  @Test
  public void customerSites() {
    Response response = request("/customer-sites/1")
        .header(HttpHeaders.AUTHORIZATION, jwtAuthorizationHeader("1"))
        .get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_SITES_NUMBER);

    // Without valid auth header
    response = request("/customer-sites/1").get();
    assertThat(status(response)).isEqualTo(UNAUTHORIZED);
  }

}

