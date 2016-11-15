package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceMock implements CustomerService {

  private List<CustomerView> mockCustomers = new ArrayList<>();
  private CustomerView mockCustomer = new CustomerView();
  private String mockCustomerId = "id1";

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers, String sarRefTag) {
    this.mockCustomerId = mockCustomerId;
    initCustomers(numberOfCustomers, sarRefTag);
  }

  private void initCustomers(int numberOfCustomers, String sarRefTag) {
    for (int i = 1; i < numberOfCustomers + 1; i++) {
      CustomerView out = new CustomerView();
      out.setCustomerId(sarRefTag + i);
      mockCustomers.add(out);
    }
  }

  public String createCustomer(CustomerParam item) {
    return mockCustomerId;
  }

  public List<CustomerView> getCustomersBySearch(String postcode) {
    return mockCustomers;
  }

  public List<CustomerView> getCustomersBySearch(String postcode, String eoriNumber) {
    return mockCustomers;
  }

  public List<CustomerView> getCustomersByUserId(String userId) {
    return mockCustomers;
  }

  public Optional<CustomerView> getCustomerById(String customerId) {
    return Optional.of(mockCustomer);
  }

  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    return mockCustomers;
  }

  public static CustomerParam getCustomerItem() {
    CustomerParam item = new CustomerParam();
    item.setUserId("userId");
    return item;
  }

}
