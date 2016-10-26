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
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireEndpoint;
import uk.gov.bis.lite.spire.SpireException;
import uk.gov.bis.lite.spire.SpireName;
import uk.gov.bis.lite.spire.SpireRequest;
import uk.gov.bis.lite.spire.SpireResponse;
import uk.gov.bis.lite.spire.SpireUnmarshaller;
import uk.gov.bis.lite.spire.SpireUtil;
import uk.gov.bis.lite.spireclient.SpireClientService;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private CompanyClient companyClient;
  private CompanyUnmarshaller companyUnmarshaller;
  private SpireClientService spireClient;

  private SpireClient spClient;
  private SpireUnmarshaller spireUnmarshaller;

  @Inject
  public CustomerService(CompanyClient companyClient, CompanyUnmarshaller companyUnmarshaller, SpireClientService spireClient,
                         SpireClient spClient, SpireUnmarshaller spireUnmarshaller) {
    this.companyClient = companyClient;
    this.companyUnmarshaller = companyUnmarshaller;
    this.spireClient = spireClient;
    this.spClient = spClient;
    this.spireUnmarshaller = spireUnmarshaller;
  }

  public String createCustomer(CustomerItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {

      // Setup Spire request
      SpireRequest request = spClient.createRequest(SpireEndpoint.CREATE_LITE_SAR);
      request.addChild(SpireName.VERSION_NO, SpireName.VERSION_1_1);
      request.addChild(SpireName.WUA_ID, item.getUserId());
      request.addChild(SpireName.CUSTOMER_NAME, item.getCustomerName());
      request.addChild(SpireName.CUSTOMER_TYPE, item.getCustomerType());
      request.addChild(SpireName.LITE_ADDRESS, SpireUtil.getAddressItemJson(item.getAddressItem()));
      request.addChild(SpireName.ADDRESS, SpireUtil.getFriendlyAddress(item.getAddressItem()));
      request.addChild(SpireName.COUNTRY_REF, item.getAddressItem().getCountry());
      request.addChild(SpireName.WEBSITE, item.getWebsite());
      String companiesHouseNumber = item.getCompaniesHouseNumber();
      if(!StringUtils.isBlank(companiesHouseNumber)) {
        request.addChild(SpireName.COMPANIES_HOUSE_NUMBER, companiesHouseNumber);
        request.addChild(SpireName.COMPANIES_HOUSE_VALIDATED, item.getCompaniesHouseValidatedStr());
      }
      String eoriNumber = item.getEoriNumber();
      if(!StringUtils.isBlank(eoriNumber)) {
        request.addChild(SpireName.EORI_NUMBER, eoriNumber);
        request.addChild(SpireName.EORI_VALIDATED, item.getEoriValidatedStr());
      }

      // Get Response and unmarshall
      SpireResponse response = spClient.sendRequest(request);
      return spireUnmarshaller.getSingleResponseElementContent(response);
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
