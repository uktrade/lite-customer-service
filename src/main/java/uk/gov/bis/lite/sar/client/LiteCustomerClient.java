package uk.gov.bis.lite.sar.client;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.xml.soap.SOAPMessage;

public class LiteCustomerClient extends SpireClient {

  private static final String VERSION_NO = "1.1";
  private static final String CREATE_LITE_SAR = "SPIRE_CREATE_LITE_SAR";
  private static final String SAR_DETAILS = "SAR_DETAILS";

  @Inject
  public LiteCustomerClient(@Named("soapLiteSarUrl") String url,
                            @Named("soapUserName") String userName,
                            @Named("soapPassword") String password) {
    super(url, userName, password);
  }

  /**
   * createLiteSar
   */
  public SOAPMessage createLiteSar(String userId, String customerName, String customerType, String liteAddress,
                                   String address, String countryRef, String website, String companiesHouseNumber,
                                   String companiesHouseValidated, String eoriNumber, String eoriValidated) {
    SOAPMessage request = getRequest(CREATE_LITE_SAR, SAR_DETAILS);
    addChild(request, "VERSION_NO", VERSION_NO);
    addChild(request, "WUA_ID", userId);
    addChild(request, "CUSTOMER_NAME", customerName);
    addChild(request, "CUSTOMER_TYPE", customerType);
    addChild(request, "LITE_ADDRESS", liteAddress);
    addChild(request, "ADDRESS", address);
    addChild(request, "COUNTRY_REF", countryRef);
    addChild(request, "WEBSITE", website);
    addChild(request, "COMPANIES_HOUSE_NUMBER", companiesHouseNumber);
    addChild(request, "COMPANIES_HOUSE_VALIDATED", companiesHouseValidated);
    addChild(request, "EORI_NUMBER", eoriNumber);
    addChild(request, "EORI_VALIDATED", eoriValidated);
    return getResponse(request);
  }
}