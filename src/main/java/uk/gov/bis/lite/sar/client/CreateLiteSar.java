package uk.gov.bis.lite.sar.client;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.util.OptConsumer;
import uk.gov.bis.lite.sar.util.Util;

import javax.xml.soap.SOAPMessage;

public class CreateLiteSar extends SpireClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateLiteSar.class);
  private static final String VERSION_NO = "1.1";
  private static final String NAMESPACE = "SPIRE_CREATE_LITE_SAR";
  private static final String CHILD_NAME = "SAR_DETAILS";

  @Inject
  public CreateLiteSar(@Named("createLiteSarUrl") String url,
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
    SOAPMessage request = getRequest(NAMESPACE, CHILD_NAME);

    addChild(request, "VERSION_NO", VERSION_NO);
    addChild(request, "WUA_ID", userId);

    OptConsumer.of(Util.opt(customerName)).ifPresent(s -> this.addChild(request, "CUSTOMER_NAME", s))
        .ifNotPresent(() -> LOGGER.warn("No CUSTOMER_NAME value"));
    OptConsumer.of(Util.opt(customerType)).ifPresent(s -> this.addChild(request, "CUSTOMER_TYPE", s))
        .ifNotPresent(() -> LOGGER.warn("No CUSTOMER_TYPE value"));
    OptConsumer.of(Util.opt(liteAddress)).ifPresent(s -> this.addChild(request, "LITE_ADDRESS", s))
        .ifNotPresent(() -> LOGGER.warn("No LITE_ADDRESS value"));
    OptConsumer.of(Util.opt(address)).ifPresent(s -> this.addChild(request, "ADDRESS", s))
        .ifNotPresent(() -> LOGGER.warn("No ADDRESS value"));
    OptConsumer.of(Util.opt(countryRef)).ifPresent(s -> this.addChild(request, "COUNTRY_REF", s))
        .ifNotPresent(() -> LOGGER.warn("No COUNTRY_REF value"));
    OptConsumer.of(Util.opt(website)).ifPresent(s -> this.addChild(request, "WEBSITE", s))
        .ifNotPresent(() -> LOGGER.warn("No WEBSITE value"));

    if(!Util.isBlank(companiesHouseNumber)) {
      addChild(request, "COMPANIES_HOUSE_NUMBER", companiesHouseNumber);
      addChild(request, "COMPANIES_HOUSE_VALIDATED", companiesHouseValidated);
    }
    if(!Util.isBlank(eoriNumber)) {
      addChild(request, "EORI_NUMBER", eoriNumber);
      addChild(request, "EORI_VALIDATED", eoriValidated);
    }
    return getResponse(request);
  }


}