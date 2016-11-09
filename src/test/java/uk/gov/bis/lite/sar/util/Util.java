package uk.gov.bis.lite.sar.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.Users;
import uk.gov.bis.lite.sar.model.item.AddressItem;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.model.item.CustomerServiceResponseItem;
import uk.gov.bis.lite.sar.model.item.SiteItem;
import uk.gov.bis.lite.sar.model.item.UserRoleItem;

import java.util.List;

import javax.ws.rs.core.Response;

public class Util {

  private static ObjectMapper mapper = new ObjectMapper();

  public static String getResponseString(Response response) {
    return getCustomerServiceResponseItem(response).getResponse();
  }

  public static int getCustomersSize(Response response) {
    List<Customer> customers = getCustomersFromResponse(response);
    return customers.size();
  }

  public static uk.gov.bis.lite.sar.model.item.Customer getCustomerResponse(Response response) {
    return response.readEntity(uk.gov.bis.lite.sar.model.item.Customer.class);
  }

  public static int getUsersUserDetailsSize(Response response) {
    Users users = getUsersFromResponse(response);
    return users.getAdministrators().size();
  }

  public static String getUserRoleItemJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getUserRoleItem());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static String getSiteItemJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getSiteItem());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static String getCustomerItemJson() {
    String json = "";
    try {
      json = mapper.writeValueAsString(getCustomerItem());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  private static CustomerServiceResponseItem getCustomerServiceResponseItem(Response response) {
    return response.readEntity(CustomerServiceResponseItem.class);
  }

  private static List<Customer> getCustomersFromResponse(Response response) {
    return (List<Customer>) response.readEntity(List.class);
  }

  private static Users getUsersFromResponse(Response response) {
    return response.readEntity(Users.class);
  }

  private static CustomerItem getCustomerItem() {
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

  private static SiteItem getSiteItem() {
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
  }

  private static UserRoleItem getUserRoleItem() {
    UserRoleItem item = new UserRoleItem();
    item.setAdminUserId("adminUserId");
    item.setRoleType("roleType");
    return item;
  }

}
