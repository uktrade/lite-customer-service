package uk.gov.bis.lite.sar.client;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.xml.soap.SOAPMessage;

public class SiteClient extends SpireClient {

  private static final String ENVELOPE_NAMESPACE = "SPIRE_COMPANY_SITES";
  private static final String CHILD_NAME = "getSites";

  @Inject
  public SiteClient(@Named("soapSiteUrl") String soapUrl, @Named("soapUserName") String clientUserName,
                    @Named("soapPassword") String clientPassword) {
    super(soapUrl, clientUserName, clientPassword);
  }

  public SOAPMessage getSitesByCustomerIdUserId(String customerId, String userId) {
    SOAPMessage request = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(request, "sarRef", customerId);
    addChild(request, "userId", userId);
    return getResponse(request);
  }
}
