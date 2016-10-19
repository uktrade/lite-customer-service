package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.CompanyClient;
import uk.gov.bis.lite.sar.client.CreateLiteSar;
import uk.gov.bis.lite.sar.client.unmarshall.CompanyUnmarshaller;
import uk.gov.bis.lite.sar.client.unmarshall.Unmarshaller;
import uk.gov.bis.lite.sar.exception.CreateException;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.model.spire.Company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private CreateLiteSar createLiteSar;
  private CompanyClient companyClient;
  private CompanyUnmarshaller companyUnmarshaller;
  private Unmarshaller unmarshaller;

  private static final String CLS_RESPONSE_ELEMENT_NAME = "SAR_REF";
  private static final String CLS_SAR_XPATH_EXPRESSION = "//*[local-name()='RESPONSE']";

  @Inject
  public CustomerService(CreateLiteSar createLiteSar,
                         CompanyClient companyClient,
                         CompanyUnmarshaller companyUnmarshaller,
                         Unmarshaller unmarshaller) {
    this.createLiteSar = createLiteSar;
    this.companyClient = companyClient;
    this.companyUnmarshaller = companyUnmarshaller;
    this.unmarshaller = unmarshaller;
  }

  public String createCustomer(CustomerItem item) {

    // Allow if we have a userId and address TODO check this is correct
    if(!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SOAPMessage message = createLiteSar.createLiteSar(item);
      return unmarshaller.getResponse(message, CLS_RESPONSE_ELEMENT_NAME, CLS_SAR_XPATH_EXPRESSION);
    } else {
      throw new CreateException("Mandatory fields missing: userId and/or address");
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
