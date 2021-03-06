package uk.gov.bis.lite.customer.mocks.permissions;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class MockCustomerService implements CustomerService {

  //For searching by EORI/postcode
  private List<CustomerView> mockCustomersForDetailSearch = new ArrayList<>();
  private List<CustomerView> mockCustomersForUserIdSearch = new ArrayList<>();
  private ObjectMapper mapper = new ObjectMapper();

  private boolean failCreateCustomer;
  private boolean failCustomersByCustomerNumber;
  private boolean missingCustomer;

  public Optional<CustomerView> createCustomer(CustomerParam param) {
    if (failCreateCustomer) {
      return Optional.empty();
    } else {
      return Optional.of(getCustomerViewMock());
    }
  }

  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    List<CustomerView> customers = new ArrayList<>();
    if (failCustomersByCustomerNumber) {
      return customers;
    } else {
      customers.add(getCustomerViewMock());
      return customers;
    }
  }

  public List<CustomerView> getCustomersByPostcode(String postcode) {
    return mockCustomersForDetailSearch;
  }

  public List<CustomerView> getCustomersByEoriNumber(String postcode, String eoriNumber) {
    return mockCustomersForDetailSearch;
  }

  public List<CustomerView> getCustomersByUserId(String userId) {
    return mockCustomersForUserIdSearch;
  }

  public Optional<CustomerView> getCustomerById(String customerId) {
    if (missingCustomer) {
      return Optional.empty();
    } else {
      return Optional.of(getCustomerViewMock());
    }
  }

  public List<CustomerView> getCustomersByName(String companyName) {
    return mockCustomersForUserIdSearch;
  }

  /**
   * Pact State setters
   */
  public void setFailCreateCustomer(boolean failCreateCustomer) {
    this.failCreateCustomer = failCreateCustomer;
  }

  public void setFailCustomersByCustomerNumber(boolean failCustomersByCustomerNumber) {
    this.failCustomersByCustomerNumber = failCustomersByCustomerNumber;
  }

  public void setMissingCustomer(boolean missingCustomer) {
    this.missingCustomer = missingCustomer;
  }

  private CustomerView getCustomerViewMock() {
    CustomerView view = null;
    try {
      view = mapper.readValue(fixture("fixture/pact/mockCustomerView.json"), CustomerView.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return view;
  }

  public void setMockCustomersForUserIdSearch(boolean hasCustomers) {
    if (hasCustomers) {
      mockCustomersForUserIdSearch = Collections.singletonList(getCustomerViewMock());
    } else {
      mockCustomersForUserIdSearch = Collections.emptyList();
    }
  }

  public void setMockCustomersForDetailSearch(boolean hasCustomers) {
    if (hasCustomers) {
      mockCustomersForDetailSearch = Collections.singletonList(getCustomerViewMock());
    } else {
      mockCustomersForDetailSearch = Collections.emptyList();
    }
  }


}
