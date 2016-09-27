package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.client.SiteClient;
import uk.gov.bis.lite.sar.client.unmarshall.SiteUnmarshaller;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.spire.SpireSite;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SiteClient client;
  private SiteUnmarshaller unmarshaller;

  @Inject
  public SiteService(SiteClient client, SiteUnmarshaller unmarshaller) {
    this.client = client;
    this.unmarshaller = unmarshaller;
  }

  public List<Site> getSites(String customerId, String userId) {
    final SOAPMessage soapMessage = client.getSitesByCustomerIdUserId(customerId, userId);
    List<SpireSite> spireSites = unmarshaller.execute(soapMessage);
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
