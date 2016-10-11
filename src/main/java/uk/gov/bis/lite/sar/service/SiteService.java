package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.CustomerApplication;
import uk.gov.bis.lite.sar.client.LiteSiteClient;
import uk.gov.bis.lite.sar.client.SiteClient;
import uk.gov.bis.lite.sar.client.unmarshall.SiteUnmarshaller;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.SiteItem;
import uk.gov.bis.lite.sar.model.spire.SpireSite;
import uk.gov.bis.lite.sar.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.soap.SOAPMessage;

@Singleton
public class SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

  private SiteClient client;
  private SiteUnmarshaller unmarshaller;
  private LiteSiteClient liteSiteClient;

  @Inject
  public SiteService(SiteClient client, LiteSiteClient liteSiteClient, SiteUnmarshaller unmarshaller) {
    this.client = client;
    this.liteSiteClient = liteSiteClient;
    this.unmarshaller = unmarshaller;
  }

  public Optional<String> createSite(SiteItem item) {
    if (CustomerApplication.MOCK) {
      return Util.optionalRef("SITE7193");
    }

    SOAPMessage soapMessage = liteSiteClient.createSite(
        item.getUserId(),
        item.getSarRef(),
        item.getDivision(), // division?
        item.getLiteAddress(),
        item.getAddress(),
        item.getCountryRef());
    Util.logSoapResponse(soapMessage);
    return Optional.empty();
  }

  public List<Site> getSites(String customerId, String userId) {
    final SOAPMessage soapMessage = client.getSitesByCustomerIdUserId(customerId, userId);
    List<SpireSite> spireSites = unmarshaller.execute(soapMessage);
    return spireSites.stream().map(Site::new).collect(Collectors.toList());
  }
}
