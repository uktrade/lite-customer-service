package uk.gov.bis.lite.sar.service;

import uk.gov.bis.lite.common.item.in.CustomerIn;
import uk.gov.bis.lite.common.item.out.CustomerOut;

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
