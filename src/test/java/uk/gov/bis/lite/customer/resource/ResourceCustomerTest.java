package uk.gov.bis.lite.customer.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
    Response response = request("/customers/1").get();
    assertThat(status(response)).isEqualTo(OK);
    //assertThat(getCustomersSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
  }

  @Test
  public void userCustomers() {
    Response response = request("/user-customers/user/1").get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
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
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);

    // With eori param
    response = target(url).queryParam("eori", "eori").request().get();
    assertEquals(400, response.getStatus());

    // With postcode and eori param
    response = target(url).queryParam("postcode", "postcode")
        .queryParam("eori", "eori").request().get();
    assertThat(status(response)).isEqualTo(OK);
    assertThat(getCustomerViewsSize(response)).isEqualTo(MOCK_CUSTOMERS_NUMBER);
  }

  @Test
  public void searchCustomersByCompanyNumber() {
    Response response = request("/search-customers/registered-number/1").get();
    assertThat(status(response)).isEqualTo(OK);
    CustomerView customer = getCustomerResponse(response);
    assertThat(customer.getCustomerId()).isEqualTo(MOCK_CUSTOMERS_SAR_REF_TAG + "1");
  }

}

