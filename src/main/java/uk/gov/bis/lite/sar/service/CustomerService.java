package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.util.Util;
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireName;
import uk.gov.bis.lite.spire.SpireRequest;
import uk.gov.bis.lite.spire.SpireResponse;
import uk.gov.bis.lite.spire.SpireUnmarshaller;
import uk.gov.bis.lite.spire.exception.SpireException;
import uk.gov.bis.lite.spire.model.SpireCompany;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private SpireClient spireClient;
  private SpireUnmarshaller spireUnmarshaller;

  @Inject
  public CustomerService(SpireClient spireClient, SpireUnmarshaller spireUnmarshaller) {
    this.spireClient = spireClient;
    this.spireUnmarshaller = spireUnmarshaller;
  }

  public String createCustomer(CustomerItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup SpireRequest
      SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.CREATE_LITE_SAR);
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

      // Get SpireResponse and unmarshall
      SpireResponse response = spireClient.sendRequest(request);
      return spireUnmarshaller.getSingleResponseElementContent(response);
    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Customer> getCustomersBySearch(String postcode) {

    // Setup SpireRequest
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.COMPANIES);
    request.addChild(SpireName.postCode, postcode);

    // Get SpireResponse and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    List<SpireCompany> companies = spireUnmarshaller.getSpireCompanies(response);

    // Map SpireCompany to Customer
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {

    // Setup SpireRequest
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.COMPANIES);
    request.addChild(SpireName.postCode, postcode);
    request.addChild(SpireName.eoriNumber, eoriNumber);

    // Get SpireResponse and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    List<SpireCompany> companies = spireUnmarshaller.getSpireCompanies(response);

    // Map SpireCompany to Customer
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {

    // Setup SpireRequest
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.COMPANIES);
    request.addChild(SpireName.userId, userId);

    // Get SpireResponse and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    List<SpireCompany> companies = spireUnmarshaller.getSpireCompanies(response);

    // Map SpireCompany to Customer
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {

    // Setup SpireRequest
    SpireRequest request = spireClient.createRequest(SpireClient.Endpoint.COMPANIES);
    request.addChild(SpireName.sarRef, customerId);

    // Get SpireResponse and unmarshall
    SpireResponse response = spireClient.sendRequest(request);
    List<SpireCompany> companies = spireUnmarshaller.getSpireCompanies(response);

    // Map SpireCompany to Customer
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }
}
