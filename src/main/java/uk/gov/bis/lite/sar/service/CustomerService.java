package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import uk.gov.bis.lite.sar.client.CustomerServiceClient;
import uk.gov.bis.lite.sar.client.unmarshall.CustomerServiceUnmarshaller;
import uk.gov.bis.lite.sar.model.Company;

import java.util.List;

import javax.xml.soap.SOAPMessage;

@Singleton
public class CustomerService {

  private CustomerServiceClient client;
  private CustomerServiceUnmarshaller unmarshaller;

  @Inject
  public CustomerService(CustomerServiceClient client, CustomerServiceUnmarshaller unmarshaller) {
    this.client = client;
    this.unmarshaller = unmarshaller;
  }

  public List<Company> getCustomerInformation(String name) {
    final SOAPMessage soapMessage = client.executeRequest(name);
    return unmarshaller.execute(soapMessage);
  }
}
