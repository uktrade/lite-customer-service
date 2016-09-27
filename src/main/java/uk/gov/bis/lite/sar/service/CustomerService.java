package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.CompanyClient;
import uk.gov.bis.lite.sar.client.unmarshall.CompanyUnmarshaller;
import uk.gov.bis.lite.sar.model.spire.Company;
import uk.gov.bis.lite.sar.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private CompanyClient client;
  private CompanyUnmarshaller unmarshaller;

  @Inject
  public CustomerService(CompanyClient client, CompanyUnmarshaller unmarshaller) {
    this.client = client;
    this.unmarshaller = unmarshaller;
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    final SOAPMessage soapMessage = client.getCompanyByPostCode(postcode);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    final SOAPMessage soapMessage = client.getCompanyByPostCodeEoriNumber(postcode, eoriNumber);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {
    final SOAPMessage soapMessage = client.getCompanyByUserId(userId);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {
    final SOAPMessage soapMessage = client.getCompanyBySarRef(customerId);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

}
