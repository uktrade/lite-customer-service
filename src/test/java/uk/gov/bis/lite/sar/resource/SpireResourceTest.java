package uk.gov.bis.lite.sar.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import uk.gov.bis.lite.common.item.AddressItem;
import uk.gov.bis.lite.common.item.in.SiteIn;
import uk.gov.bis.lite.common.item.out.SiteOut;
import uk.gov.bis.lite.sar.mocks.CustomerServiceMock;
import uk.gov.bis.lite.sar.mocks.SiteServiceMock;
import uk.gov.bis.lite.sar.mocks.UserServiceMock;
import uk.gov.bis.lite.sar.model.Users;
import uk.gov.bis.lite.sar.model.item.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.model.item.CustomerServiceResponseItem;
import uk.gov.bis.lite.sar.model.item.UserRoleItem;

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
    List<Customer> customers = getCustomersFromResponse(response);
    return customers.size();
  }

  Customer getCustomerResponse(Response response) {
    return response.readEntity(Customer.class);
  }


  SiteOut getSiteItemOutResponse(Response response) {
    return response.readEntity(SiteOut.class);
  }

  int getUsersUserDetailsSize(Response response) {
    Users users = getUsersFromResponse(response);
    return users.getAdministrators().size();
  }

  String getUserRoleItemJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getUserRoleItem());
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

  private CustomerServiceResponseItem getCustomerServiceResponseItem(Response response) {
    return response.readEntity(CustomerServiceResponseItem.class);
  }

  private List<Customer> getCustomersFromResponse(Response response) {
    return (List<Customer>) response.readEntity(List.class);
  }

  private Users getUsersFromResponse(Response response) {
    return response.readEntity(Users.class);
  }

  private CustomerItem getCustomerItem() {
    AddressItem address = new AddressItem();
    address.setLine1("line1");
    address.setLine2("line2");
    address.setCounty("county");
    address.setPostcode("postcode");
    address.setCountry("country");

    CustomerItem customer = new CustomerItem();
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

  /*
  private SiteItem getSiteItem() {
    AddressItem address = new AddressItem();
    address.setLine1("line1");
    address.setLine2("line2");
    address.setCounty("county");
    address.setPostcode("postcode");
    address.setCountry("country");

    SiteItem site = new SiteItem();
    site.setSarRef("sarRef");
    site.setSiteName("siteName");
    site.setUserId("userId");
    site.setAddressItem(address);
    return site;
  }*/

  private UserRoleItem getUserRoleItem() {
    UserRoleItem item = new UserRoleItem();
    item.setAdminUserId("adminUserId");
    item.setRoleType("roleType");
    return item;
  }
}
