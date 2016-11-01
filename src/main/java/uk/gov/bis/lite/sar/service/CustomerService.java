package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.spire.SpireCompanyClient;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.util.Util;
import uk.gov.bis.lite.spire.client.SpireName;
import uk.gov.bis.lite.spire.client.SpireRequest;
import uk.gov.bis.lite.spire.client.exception.SpireException;

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
      request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_1);
      request.addChild(SpireName.WUA_ID, item.getUserId());
      request.addChild(SpireName.CUSTOMER_NAME, item.getCustomerName());
      request.addChild(SpireName.CUSTOMER_TYPE, item.getCustomerType());
      request.addChild(SpireName.LITE_ADDRESS, Util.getAddressItemJson(item.getAddressItem()));
      request.addChild(SpireName.ADDRESS, Util.getFriendlyAddress(item.getAddressItem()));
      request.addChild(SpireName.COUNTRY_REF, item.getAddressItem().getCountry());
      request.addChild(SpireName.WEBSITE, item.getWebsite());
      String companiesHouseNumber = item.getCompaniesHouseNumber();
      if (!StringUtils.isBlank(companiesHouseNumber)) {
        request.addChild(SpireName.COMPANIES_HOUSE_NUMBER, companiesHouseNumber);
        request.addChild(SpireName.COMPANIES_HOUSE_VALIDATED, item.getCompaniesHouseValidatedStr());
      }
      String eoriNumber = item.getEoriNumber();
      if (!StringUtils.isBlank(eoriNumber)) {
        request.addChild(SpireName.EORI_NUMBER, eoriNumber);
        request.addChild(SpireName.EORI_VALIDATED, item.getEoriValidatedStr());
      }
      return createLiteSarReferenceClient.getResult(request);
    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    SpireRequest request = companyClient.createRequest();
    request.addChild(SpireName.postCode, postcode);
    return companyClient.getResult(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    SpireRequest request = companyClient.createRequest();
    request.addChild(SpireName.postCode, postcode);
    request.addChild(SpireName.eoriNumber, eoriNumber);
    return companyClient.getResult(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild(SpireName.userId, userId);
    return companyClient.getResult(request).stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild(SpireName.sarRef, customerId);
    return companyClient.getResult(request).stream().map(Customer::new).collect(Collectors.toList());
  }
}
