package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.item.CustomerItem;
import uk.gov.bis.lite.sar.spire.SpireCompanyClient;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.util.Util;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CustomerServiceImpl implements CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private SpireReferenceClient createLiteSarReferenceClient;
  private SpireCompanyClient companyClient;

  @Inject
  public CustomerServiceImpl(@Named("SpireCreateLiteSarClient") SpireReferenceClient createLiteSarReferenceClient,
                             SpireCompanyClient companyClient) {
    this.createLiteSarReferenceClient = createLiteSarReferenceClient;
    this.companyClient = companyClient;
  }

  public String createCustomer(CustomerItem item) {
    // Allow if we have a userId and address TODO check this is correct
    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SpireRequest request = createLiteSarReferenceClient.createRequest();
      Util.addChild(request, "VERSION_NO", "1.1");
      Util.addChild(request, "WUA_ID", item.getUserId());
      Util.addChild(request, "CUSTOMER_NAME", item.getCustomerName());
      Util.addChild(request, "CUSTOMER_TYPE", item.getCustomerType());
      Util.addChild(request, "LITE_ADDRESS", Util.getAddressItemJson(item.getAddressItem()));
      Util.addChild(request, "ADDRESS", Util.getFriendlyAddress(item.getAddressItem()));
      Util.addChild(request, "COUNTRY_REF", item.getAddressItem().getCountry());
      Util.addChild(request, "WEBSITE", item.getWebsite());
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

  public List<Customer> getCustomersByCompanyNumber(String companyNumber) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("companyNumber", companyNumber);
    return companyClient.sendRequest(request).stream().map(Customer::new).collect(Collectors.toList());
  }
}
