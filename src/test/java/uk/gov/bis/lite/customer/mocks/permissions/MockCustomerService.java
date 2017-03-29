package uk.gov.bis.lite.customer.mocks.permissions;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockCustomerService implements CustomerService, PermissionsPactConstants {

  private List<CustomerView> mockCustomers = new ArrayList<>();
  private ObjectMapper mapper = new ObjectMapper();

  public Optional<CustomerView> createCustomer(CustomerParam param) {
    if(StringUtils.isBlank(param.getUserId())) {
      return Optional.empty();
    }
    return Optional.of(getCustomerViewMock());
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
    return Optional.of(getCustomerViewMock());
  }

  public List<CustomerView> getCustomersByCompanyNumber(String companyNumber) {
    List<CustomerView> mockCustomers = new ArrayList<>();
    if(companyNumber.equals(COMPANY_NUMBER_SUCCESS)) {
      mockCustomers.add(getCustomerViewMock());
    }
    return mockCustomers;
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

}
