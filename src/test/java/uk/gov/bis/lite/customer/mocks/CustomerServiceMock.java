package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceMock implements CustomerService {

  private List<CustomerView> mockCustomers = new ArrayList<>();
  private CustomerView mockCustomerView;

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers, String sarRefTag) {
    initCustomers(mockCustomerId, numberOfCustomers, sarRefTag);
  }

  private void initCustomers(String mockCustomerId, int numberOfCustomers, String sarRefTag) {

    CustomerView view = new CustomerView();
    view.setCustomerId(mockCustomerId);
    mockCustomerView = view;

    for (int i = 1; i < numberOfCustomers + 1; i++) {
      CustomerView out = new CustomerView();
      out.setCustomerId(sarRefTag + i);
      mockCustomers.add(out);
    }
  }

  public Optional<CustomerView> createCustomer(CustomerParam param) {
    return Optional.of(mockCustomerView);
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
    return Optional.of(mockCustomerView);
  }

  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    return mockCustomers;
  }

  public List<CustomerView> getCustomersByName(String companyName) {
    return mockCustomers;
  }
}
