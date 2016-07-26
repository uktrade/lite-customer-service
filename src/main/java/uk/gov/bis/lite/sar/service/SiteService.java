package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import uk.gov.bis.lite.sar.client.CustomerServiceClient;
import uk.gov.bis.lite.sar.client.SiteServiceClient;
import uk.gov.bis.lite.sar.client.unmarshall.CustomerServiceUnmarshaller;
import uk.gov.bis.lite.sar.client.unmarshall.SiteServiceUnmarshaller;
import uk.gov.bis.lite.sar.model.Company;
import uk.gov.bis.lite.sar.model.Site;

import java.util.List;

import javax.xml.soap.SOAPMessage;

@Singleton
public class SiteService {
  private SiteServiceClient client;
  private SiteServiceUnmarshaller unmarshaller;

  @Inject
  public SiteService(SiteServiceClient client, SiteServiceUnmarshaller unmarshaller) {
    this.client = client;
    this.unmarshaller = unmarshaller;
  }

  public List<Site> getCustomerInformation(String sarRef) {
    final SOAPMessage soapMessage = client.executeRequest(sarRef);
    return unmarshaller.execute(soapMessage, sarRef);
  }

}
