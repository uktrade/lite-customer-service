package uk.gov.bis.lite.customer.resource;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import uk.gov.bis.lite.common.jwt.LiteJwtAuthFilterHelper;
import uk.gov.bis.lite.common.jwt.LiteJwtConfig;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.common.jwt.LiteJwtUserHelper;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.AddressParam;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.mocks.CustomerServiceMock;
import uk.gov.bis.lite.customer.mocks.SiteServiceMock;
import uk.gov.bis.lite.customer.mocks.UserServiceMock;

import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class SpireResourceTest {

  final int OK = Response.Status.OK.getStatusCode();
  final int UNAUTHORIZED = Response.Status.UNAUTHORIZED.getStatusCode();

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
  private static UserServiceMock mockUserService = new UserServiceMock(MOCK_USERS_USER_DETAIL_NUMBER);

  public static final String JWT_SHARED_SECRET = "demo-secret-which-is-very-long-so-as-to-hit-the-byte-requirement";

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addProvider(new AuthDynamicFeature(LiteJwtAuthFilterHelper.buildAuthFilter(JWT_SHARED_SECRET)))
      .addProvider(new AuthValueFactoryProvider.Binder<>(LiteJwtUser.class))
      .addResource(new CustomerResource(mockCustomerService))
      .addResource(new CustomerCreateResource(mockCustomerService))
      .addResource(new SiteResource(mockSiteService))
      .addResource(new CustomerSiteResource(mockSiteService))
      .addResource(new UserResource(mockUserService))
      .build();


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

  int getCustomerViewsSize(Response response) {
    return getCustomerViewsFromResponse(response).size();
  }

  CustomerView getCustomerResponse(Response response) {
    return response.readEntity(CustomerView.class);
  }

  int getUsersUserDetailsSize(Response response) {
    UsersResponse users = getUsersFromResponse(response);
    return users.getAdministrators().size();
  }

  SiteView getResponseSiteView(Response response) {
    return response.readEntity(SiteView.class);
  }

  CustomerView getResponseCustomerView(Response response) {
    return response.readEntity(CustomerView.class);
  }

  private List<CustomerView> getCustomerViewsFromResponse(Response response) {
    return (List<CustomerView>) response.readEntity(List.class);
  }

  private UsersResponse getUsersFromResponse(Response response) {
    return response.readEntity(UsersResponse.class);
  }

  CustomerParam getCustomerParam() {
    AddressParam addressParam = new AddressParam();
    addressParam.setLine1("line1");
    addressParam.setLine2("line2");
    addressParam.setCounty("county");
    addressParam.setPostcode("postcode");
    addressParam.setCountry("country");

    CustomerParam customerParam = new CustomerParam();
    customerParam.setUserId("userId");
    customerParam.setCustomerName("userId");
    customerParam.setCustomerType("userId");
    customerParam.setAddressParam(addressParam);
    customerParam.setWebsite("userId");
    customerParam.setCompaniesHouseNumber("userId");
    customerParam.setCompaniesHouseValidated(false);
    customerParam.setEoriNumber("userId");
    customerParam.setEoriValidated(false);
    return customerParam;
  }

  SiteParam getSiteParam() {
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

  UserRoleParam getUserRoleParam() {
    UserRoleParam param = new UserRoleParam();
    param.setAdminUserId("adminUserId");
    param.setRoleType(UserRoleParam.RoleType.ADMIN);
    return param;
  }

  public static String jwtAuthorizationHeader(String userId) {
    LiteJwtUserHelper liteJwtUserHelper = new LiteJwtUserHelper(new LiteJwtConfig(JWT_SHARED_SECRET, "some-lite-service"));
    LiteJwtUser liteJwtUser = new LiteJwtUser()
        .setUserId(userId)
        .setEmail("test@test.com")
        .setFullName("Mr Test");
    return liteJwtUserHelper.generateTokenInAuthHeaderFormat(liteJwtUser);
  }
}
