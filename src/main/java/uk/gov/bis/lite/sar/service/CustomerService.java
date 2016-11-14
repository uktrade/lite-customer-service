package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

  String createCustomer(CustomerItem item);

  List<Customer> getCustomersBySearch(String postcode);

  List<Customer> getCustomersBySearch(String postcode, String eoriNumber);

  List<Customer> getCustomersByUserId(String userId);

  Optional<Customer> getCustomerById(String customerId);

  List<Customer> getCustomersByCompanyNumber(String companyNumber);
}
