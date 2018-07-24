package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

  Optional<CustomerView> createCustomer(CustomerParam param);

  List<CustomerView> getCustomersByPostcode(String postcode);

  List<CustomerView> getCustomersByEoriNumber(String postcode, String eoriNumber);

  List<CustomerView> getCustomersByUserId(String userId);

  Optional<CustomerView> getCustomerById(String customerId);

  List<CustomerView> getCustomersByCompanyNumber(String companyNumber);

  List<CustomerView> getCustomersByName(String companyName);
}
