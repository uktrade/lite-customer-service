package uk.gov.bis.lite.customer.service;

import uk.gov.bis.lite.customer.api.item.in.CustomerIn;
import uk.gov.bis.lite.customer.api.item.out.CustomerOut;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

  String createCustomer(CustomerIn item);

  List<CustomerOut> getCustomersBySearch(String postcode);

  List<CustomerOut> getCustomersBySearch(String postcode, String eoriNumber);

  List<CustomerOut> getCustomersByUserId(String userId);

  Optional<CustomerOut> getCustomerById(String customerId);

  List<CustomerOut> getCustomersByCompanyNumber(String companyNumber);
}
