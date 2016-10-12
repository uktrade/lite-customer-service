package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.CustomerApplication;
import uk.gov.bis.lite.sar.client.CompanyClient;
import uk.gov.bis.lite.sar.client.CreateLiteSar;
import uk.gov.bis.lite.sar.client.unmarshall.CompanyUnmarshaller;
import uk.gov.bis.lite.sar.client.unmarshall.CreateLiteSarUnmarshaller;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.model.spire.Company;
import uk.gov.bis.lite.sar.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private CreateLiteSar createLiteSar;
  private CompanyClient companyClient;
  private CompanyUnmarshaller unmarshaller;
  private CreateLiteSarUnmarshaller createLiteSarUnmarshaller;

  @Inject
  public CustomerService(CreateLiteSar createLiteSar, 
                         CompanyClient companyClient,
                         CompanyUnmarshaller unmarshaller,
                         CreateLiteSarUnmarshaller createLiteSarUnmarshaller) {
    this.createLiteSar = createLiteSar;
    this.companyClient = companyClient;
    this.unmarshaller = unmarshaller;
    this.createLiteSarUnmarshaller = createLiteSarUnmarshaller;
  }

  public Optional<String> createCustomer(CustomerItem item) {
    if (CustomerApplication.MOCK) {
      return Util.optionalRef("SAR7193");
    }

    SOAPMessage soapMessage = createLiteSar.createLiteSar(
        item.getUserId(),
        item.getCustomerName(),
        item.getCustomerType(),
        item.getLiteAddress(),
        item.getAddress(),
        item.getCountryRef(),
        item.getWebsite(),
        item.getCompaniesHouseNumber(),
        item.getCompaniesHouseValidated().toString(),
        item.getEoriNumber(),
        item.getEoriValidated().toString());



    return Optional.empty();
  }

  public List<Customer> getCustomersBySearch(String postcode) {
    final SOAPMessage soapMessage = companyClient.getCompanyByPostCode(postcode);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersBySearch(String postcode, String eoriNumber) {
    final SOAPMessage soapMessage = companyClient.getCompanyByPostCodeEoriNumber(postcode, eoriNumber);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersByUserId(String userId) {
    final SOAPMessage soapMessage = companyClient.getCompanyByUserId(userId);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Customer> getCustomersById(String customerId) {
    final SOAPMessage soapMessage = companyClient.getCompanyBySarRef(customerId);
    List<Company> companies = unmarshaller.execute(soapMessage);
    return companies.stream().map(Customer::new).collect(Collectors.toList());
  }
}
