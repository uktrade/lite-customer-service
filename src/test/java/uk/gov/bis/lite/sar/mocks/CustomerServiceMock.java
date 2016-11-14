package uk.gov.bis.lite.sar.mocks;

import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.service.CustomerService;
import uk.gov.bis.lite.sar.spire.model.SpireCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceMock implements CustomerService {

  private List<Customer> mockCustomers = new ArrayList<>();
  private Customer mockCustomer = new Customer(new SpireCompany());
  private String mockCustomerId = "id1";

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers, String sarRefTag) {
    this.mockCustomerId = mockCustomerId;
    initCustomers(numberOfCustomers, sarRefTag);
  }

  private void initCustomers(int numberOfCustomers, String sarRefTag) {
    for (int i = 1; i < numberOfCustomers + 1; i++) {
      SpireCompany company = new SpireCompany();
      company.setSarRef(sarRefTag + i);
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

  public Optional<Customer> getCustomerById(String customerId) {
    return Optional.of(mockCustomer);
  }

  public List<Customer> getCustomersByCompanyNumber(String companyNumber) {
    return mockCustomers;
  }

  public static CustomerItem getCustomerItem() {
    CustomerItem item = new CustomerItem();
    item.setUserId("userId");
    return item;
  }

}
