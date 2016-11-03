package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.spire.SpireCompanyClient;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.util.Util;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private SpireReferenceClient createLiteSarReferenceClient;
  private SpireCompanyClient companyClient;

  @Inject
  public CustomerService(@Named("SpireCreateLiteSarClient") SpireReferenceClient createLiteSarReferenceClient,
                         SpireCompanyClient companyClient) {
    this.createLiteSarReferenceClient = createLiteSarReferenceClient;
    this.companyClient = companyClient;
  }

  public String createCustomer(CustomerItem item) {
    // Allow if we have a userId and address TODO check this is correct
    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SpireRequest request = createLiteSarReferenceClient.createRequest();
      request.addChild("VERSION_NO", "1.1");
      request.addChild("WUA_ID", item.getUserId());
      request.addChild("CUSTOMER_NAME", item.getCustomerName());
      request.addChild("CUSTOMER_TYPE", item.getCustomerType());
      request.addChild("LITE_ADDRESS", Util.getAddressItemJson(item.getAddressItem()));
      request.addChild("ADDRESS", Util.getFriendlyAddress(item.getAddressItem()));
      request.addChild("COUNTRY_REF", item.getAddressItem().getCountry());
      request.addChild("WEBSITE", item.getWebsite());
      String companiesHouseNumber = item.getCompaniesHouseNumber();
      if (!StringUtils.isBlank(companiesHouseNumber)) {
        request.addChild("COMPANIES_HOUSE_NUMBER", companiesHouseNumber);
        request.addChild("COMPANIES_HOUSE_VALIDATED", item.getCompaniesHouseValidatedStr());
      }
      String eoriNumber = item.getEoriNumber();
      if (!StringUtils.isBlank(eoriNumber)) {
        request.addChild("EORI_NUMBER", eoriNumber);
        request.addChild("EORI_VALIDATED", item.getEoriValidatedStr());
      }
      return createLiteSarReferenceClient.sendRequest(request);
    } else {
      throw new SpireClientException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("postCode", postcode);
    return companyClient.sendRequest(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("postCode", postcode);
    request.addChild("eoriNumber", eoriNumber);
    return companyClient.sendRequest(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("userId", userId);
    return companyClient.sendRequest(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("sarRef", customerId);
    return companyClient.sendRequest(request).stream().map(Customer::new).collect(Collectors.toList());
  }
}
