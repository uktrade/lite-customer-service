package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.item.in.CustomerIn;
import uk.gov.bis.lite.customer.api.item.out.CustomerOut;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceMock implements CustomerService {

  private List<CustomerOut> mockCustomers = new ArrayList<>();
  private CustomerOut mockCustomer = new CustomerOut();
  private String mockCustomerId = "id1";

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers, String sarRefTag) {
    this.mockCustomerId = mockCustomerId;
    initCustomers(numberOfCustomers, sarRefTag);
  }

  private void initCustomers(int numberOfCustomers, String sarRefTag) {
    for (int i = 1; i < numberOfCustomers + 1; i++) {
      CustomerOut out = new CustomerOut();
      out.setSarRef(sarRefTag + i);
      mockCustomers.add(out);
    }
  }

  public String createCustomer(CustomerIn item) {
    return mockCustomerId;
  }

  public List<CustomerOut> getCustomersBySearch(String postcode) {
    return mockCustomers;
  }

  public List<CustomerOut> getCustomersBySearch(String postcode, String eoriNumber) {
    return mockCustomers;
  }

  public List<CustomerOut> getCustomersByUserId(String userId) {
    return mockCustomers;
  }

  public Optional<CustomerOut> getCustomerById(String customerId) {
    return Optional.of(mockCustomer);
  }

  public List<CustomerOut> getCustomersByCompanyNumber(String companyNumber) {
    return mockCustomers;
  }

  public static CustomerIn getCustomerItem() {
    CustomerIn item = new CustomerIn();
    item.setUserId("userId");
    return item;
  }

}
