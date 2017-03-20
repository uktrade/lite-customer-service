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
  private Optional<CustomerView> mockCustomerView;

  public CustomerServiceMock() {
    this("EXISTING_CUSTOMER", 1, "SAR1");
  }

  public CustomerServiceMock(String mockCustomerId, int numberOfCustomers, String sarRefTag) {
    initCustomers(mockCustomerId, numberOfCustomers, sarRefTag);
  }

  private void initCustomers(String mockCustomerId, int numberOfCustomers, String sarRefTag) {

    mockCustomer.setCustomerId(mockCustomerId);
    mockCustomer.setApplicantType("ORGANISATION");
    mockCustomer.setCompanyName("XYZ Company");
    mockCustomer.setCompanyNumber("12345");
    mockCustomer.setOrganisationType("Trading Company");
    mockCustomer.setRegistrationStatus("REGISTERED");
    mockCustomer.setShortName("XYZ");
    mockCustomer.setWebsites(new ArrayList<>());

    CustomerView view = new CustomerView();
    view.setCustomerId(mockCustomerId);
    mockCustomerView = Optional.of(view);

    for (int i = 1; i < numberOfCustomers + 1; i++) {
      CustomerView out = new CustomerView();
      out.setCustomerId(sarRefTag + i);
      out.setApplicantType("ORGANISATION");
      out.setCompanyName("XYZ Company" + i);
      out.setCompanyNumber("12345" + i);
      out.setOrganisationType("Trading Company");
      out.setRegistrationStatus("REGISTERED");
      out.setShortName("XYZ" + i);
      out.setWebsites(new ArrayList<>());
      mockCustomers.add(out);
    }
  }

  public Optional<CustomerView> createCustomer(CustomerParam param) {
    return mockCustomerView;
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
    if ("EXISTING_CUSTOMER".equals(customerId)) {
      return Optional.of(mockCustomer);
    }
    return Optional.empty();
  }

  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    return mockCustomers;
  }

}
