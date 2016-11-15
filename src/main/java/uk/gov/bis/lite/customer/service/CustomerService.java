package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

  String createCustomer(CustomerParam item);

  List<CustomerView> getCustomersBySearch(String postcode);

  List<CustomerView> getCustomersBySearch(String postcode, String eoriNumber);

  List<CustomerView> getCustomersByUserId(String userId);

  Optional<CustomerView> getCustomerById(String customerId);

  List<CustomerView> getCustomersByCompanyNumber(String companyNumber);
}
