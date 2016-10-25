package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.CompanyClient;
import uk.gov.bis.lite.sar.client.unmarshall.CompanyUnmarshaller;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.model.spire.Company;
import uk.gov.bis.lite.spireclient.SpireClientService;
import uk.gov.bis.lite.spireclient.model.SpireRequest;
import uk.gov.bis.lite.spireclient.model.SpireResponse;
import uk.gov.bis.lite.spireclient.spire.SpireException;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private CompanyClient companyClient;
  private CompanyUnmarshaller companyUnmarshaller;
  private SpireClientService spireClient;

  @Inject
  public CustomerService(CompanyClient companyClient,
                         CompanyUnmarshaller companyUnmarshaller,
                         SpireClientService spireClient) {
    this.companyClient = companyClient;
    this.companyUnmarshaller = companyUnmarshaller;
    this.spireClient = spireClient;
  }

  public String createCustomer(CustomerItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup Spire request
      SpireRequest request = spireClient.getSpireRequest(SpireClientService.Endpoint.CREATE_LITE_SAR, item.getUserId());
      request.setAddressData(item.getAddressItem());
      request.setCustomerName(item.getCustomerName());
      request.setCustomerType(item.getCustomerType());
      request.setWebsite(item.getWebsite());
      request.setCompaniesHouseNumber(item.getCompaniesHouseNumber());
      request.setCompaniesHouseValidated(item.getCompaniesHouseValidated());
      request.setEoriNumber(item.getEoriNumber());
      request.setEoriValidated(item.getEoriValidated());

      // Execute Spire Request
      SpireResponse response = spireClient.executeRequest(request);

      LOGGER.info("SpireResponse: " + response.getInfo());

      return response.getRef();
    } else {
      throw new SpireException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    final SOAPMessage soapMessage = companyClient.getCompanyByPostCode(postcode);
    List<Company> companies = companyUnmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    final SOAPMessage soapMessage = companyClient.getCompanyByPostCodeEoriNumber(postcode, eoriNumber);
    List<Company> companies = companyUnmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {
    final SOAPMessage soapMessage = companyClient.getCompanyByUserId(userId);
    List<Company> companies = companyUnmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {
    final SOAPMessage soapMessage = companyClient.getCompanyBySarRef(customerId);
    List<Company> companies = companyUnmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

}
