package uk.gov.bis.lite.customer.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import uk.gov.bis.lite.customer.api.item.AddressItem;
import uk.gov.bis.lite.customer.api.item.in.CustomerIn;
import uk.gov.bis.lite.customer.api.item.in.SiteIn;
import uk.gov.bis.lite.customer.api.item.in.UserRoleIn;
import uk.gov.bis.lite.customer.api.item.out.CustomerOut;
import uk.gov.bis.lite.customer.api.item.out.CustomerServiceOut;
import uk.gov.bis.lite.customer.api.item.out.SiteOut;
import uk.gov.bis.lite.customer.api.item.out.UsersOut;
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
    List<CustomerOut> customers = getCustomersFromResponse(response);
    return customers.size();
  }

  CustomerOut getCustomerResponse(Response response) {
    return response.readEntity(CustomerOut.class);
  }

  SiteOut getSiteItemOutResponse(Response response) {
    return response.readEntity(SiteOut.class);
  }

  int getUsersUserDetailsSize(Response response) {
    UsersOut users = getUsersFromResponse(response);
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

  private CustomerServiceOut getCustomerServiceResponseItem(Response response) {
    return response.readEntity(CustomerServiceOut.class);
  }

  private List<CustomerOut> getCustomersFromResponse(Response response) {
    return (List<CustomerOut>) response.readEntity(List.class);
  }

  private UsersOut getUsersFromResponse(Response response) {
    return response.readEntity(UsersOut.class);
  }

  private CustomerIn getCustomerItem() {
    AddressItem address = new AddressItem();
    address.setLine1("line1");
    address.setLine2("line2");
    address.setCounty("county");
    address.setPostcode("postcode");
    address.setCountry("country");

    CustomerIn customer = new CustomerIn();
    customer.setUserId("userId");
    customer.setCustomerName("userId");
    customer.setCustomerType("userId");
    customer.setAddressItem(address);
    customer.setWebsite("userId");
    customer.setCompaniesHouseNumber("userId");
    customer.setCompaniesHouseValidated(false);
    customer.setEoriNumber("userId");
    customer.setEoriValidated(false);
    return customer;
  }

  private SiteIn getSiteItemIn() {
    AddressItem address = new AddressItem();
    address.setLine1("line1");
    address.setLine2("line2");
    address.setCounty("county");
    address.setPostcode("postcode");
    address.setCountry("country");

    SiteIn site = new SiteIn();
    site.setSiteName("siteName");
    site.setAddress(address);
    return site;
  }

  private UserRoleIn getUserRoleIn() {
    UserRoleIn item = new UserRoleIn();
    item.setAdminUserId("adminUserId");
    item.setRoleType(UserRoleIn.RoleType.ADMIN);
    return item;
  }
}
