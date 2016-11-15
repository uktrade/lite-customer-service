package uk.gov.bis.lite.customer.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import uk.gov.bis.lite.customer.api.param.AddressParam;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.api.ApiResponse;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.mocks.CustomerServiceMock;
import uk.gov.bis.lite.customer.mocks.SiteServiceMock;
import uk.gov.bis.lite.customer.mocks.UserServiceMock;

import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class SpireResourceTest {

  private static ObjectMapper mapper = new ObjectMapper();
  final int OK = Response.Status.OK.getStatusCode();

  // CustomerServiceMock setup
  static int MOCK_CUSTOMERS_NUMBER = 1;
  static String MOCK_CUSTOMERS_SAR_REF_TAG = "REF";
  static String MOCK_CUSTOMER_ID = "id1";
  private static CustomerServiceMock mockCustomerService = new CustomerServiceMock(MOCK_CUSTOMER_ID, MOCK_CUSTOMERS_NUMBER, MOCK_CUSTOMERS_SAR_REF_TAG);

  // SiteServiceMock setup
  static int MOCK_SITES_NUMBER = 1;
  static String MOCK_SITE_ID = "id1";
  private static SiteServiceMock mockSiteService = new SiteServiceMock(MOCK_SITE_ID, MOCK_SITES_NUMBER);

  // UserServiceMock setup
  static int MOCK_USERS_USER_DETAIL_NUMBER = 3;
  static String MOCK_COMPLETE = "COMPLETE";
  private static UserServiceMock mockUserService = new UserServiceMock(MOCK_COMPLETE, MOCK_USERS_USER_DETAIL_NUMBER);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new CustomerResource(mockCustomerService))
      .addResource(new CustomerCreateResource(mockCustomerService))
      .addResource(new SiteResource(mockSiteService))
      .addResource(new SiteCreateResource(mockSiteService))
      .addResource(new UserResource(mockUserService)).build();


  Invocation.Builder request(String url, String mediaType) {
    return target(url).request(mediaType);
  }

  Invocation.Builder request(String url) {
    return target(url).request();
  }

  WebTarget target(String url) {
    return resources.client().target(url);
  }

  int status(Response response) {
    return response.getStatus();
  }

  String getResponseString(Response response) {
    return getCustomerServiceResponseItem(response).getResponse();
  }

  int getCustomersSize(Response response) {
    List<CustomerView> customers = getCustomersFromResponse(response);
    return customers.size();
  }

  CustomerView getCustomerResponse(Response response) {
    return response.readEntity(CustomerView.class);
  }

  SiteView getSiteItemOutResponse(Response response) {
    return response.readEntity(SiteView.class);
  }

  int getUsersUserDetailsSize(Response response) {
    UsersResponse users = getUsersFromResponse(response);
    return users.getAdministrators().size();
  }

  String getUserRoleInJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getUserRoleIn());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  String getSiteItemInJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getSiteItemIn());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  String getCustomerItemJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getCustomerItem());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  private ApiResponse getCustomerServiceResponseItem(Response response) {
    return response.readEntity(ApiResponse.class);
  }

  private List<CustomerView> getCustomersFromResponse(Response response) {
    return (List<CustomerView>) response.readEntity(List.class);
  }

  private UsersResponse getUsersFromResponse(Response response) {
    return response.readEntity(UsersResponse.class);
  }

  private CustomerParam getCustomerItem() {
    AddressParam addressParam = new AddressParam();
    addressParam.setLine1("line1");
    addressParam.setLine2("line2");
    addressParam.setCounty("county");
    addressParam.setPostcode("postcode");
    addressParam.setCountry("country");

    CustomerParam customer = new CustomerParam();
    customer.setUserId("userId");
    customer.setCustomerName("userId");
    customer.setCustomerType("userId");
    customer.setAddressParam(addressParam);
    customer.setWebsite("userId");
    customer.setCompaniesHouseNumber("userId");
    customer.setCompaniesHouseValidated(false);
    customer.setEoriNumber("userId");
    customer.setEoriValidated(false);
    return customer;
  }

  private SiteParam getSiteItemIn() {
    AddressParam addressParam = new AddressParam();
    addressParam.setLine1("line1");
    addressParam.setLine2("line2");
    addressParam.setCounty("county");
    addressParam.setPostcode("postcode");
    addressParam.setCountry("country");

    SiteParam site = new SiteParam();
    site.setSiteName("siteName");
    site.setAddressParam(addressParam);
    return site;
  }

  private UserRoleParam getUserRoleIn() {
    UserRoleParam item = new UserRoleParam();
    item.setAdminUserId("adminUserId");
    item.setRoleType(UserRoleParam.RoleType.ADMIN);
    return item;
  }
}
