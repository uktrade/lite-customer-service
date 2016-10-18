package uk.gov.bis.lite.sar.client;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import uk.gov.bis.lite.sar.model.SiteItem;

import javax.xml.soap.SOAPMessage;

public class CreateSiteForSar extends SpireClient {

  private static final String VERSION_NO = "1.0";
  private static final String NAMESPACE = "SPIRE_CREATE_SITE_FOR_SAR";
  private static final String CHILD_NAME = "SITE_DETAILS";

  @Inject
  public CreateSiteForSar(@Named("createSiteForSarUrl") String url,
                          @Named("soapUserName") String userName,
                          @Named("soapPassword") String password) {
    super(url, userName, password);
  }

  /**
   * createSite
   */
  public SOAPMessage createSite(SiteItem item) {

    SOAPMessage request = getRequest(NAMESPACE, CHILD_NAME);
    addChild(request, "VERSION_NO", VERSION_NO);

    addChild(request, "WUA_ID", item.getUserId());
    addChild(request, "SAR_REF", item.getSarRef());
    addChild(request, "DIVISION", item.getSiteName());
    addChild(request, "LITE_ADDRESS", getAddressItemJson(item.getAddressItem()));
    addChild(request, "ADDRESS", getFriendlyAddress(item.getAddressItem()));
    addChild(request, "COUNTRY_REF", item.getAddressItem().getCountry());

    return getResponse(request);
  }
}
