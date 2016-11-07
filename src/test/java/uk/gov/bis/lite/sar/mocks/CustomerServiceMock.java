package uk.gov.bis.lite.sar.mocks;

import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.service.CustomerService;
import uk.gov.bis.lite.sar.spire.model.SpireCompany;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceMock implements CustomerService {

  private List<Customer> mockCustomers = new ArrayList<>();
  private String mockCustomerId = "id1";

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers) {
    this.mockCustomerId = mockCustomerId;
    initCustomers(numberOfCustomers);
  }

  private void initCustomers(int numberOfCustomers) {
    for (int i = 0; i < numberOfCustomers; i++) {
      SpireCompany company = new SpireCompany();
      company.setSarRef("sarRef" + i);
      company.setName("Name" + i);
      mockCustomers.add(new Customer(company));
    }
  }

  public String createCustomer(CustomerItem item) {
    return mockCustomerId;
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    return mockCustomers;
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    return mockCustomers;
  }

  public List<Customer> getCustomersByUserId(String userId) {
    return mockCustomers;
  }

  public List<Customer> getCustomersById(String customerId) {
    return mockCustomers;
  }

  public static CustomerItem getCustomerItem() {
    CustomerItem item = new CustomerItem();
    item.setUserId("userId");
    return item;
  }

}
