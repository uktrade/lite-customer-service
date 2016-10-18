package uk.gov.bis.lite.sar.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import uk.gov.bis.lite.sar.model.CustomerItem;

import javax.xml.soap.SOAPMessage;

public class CreateLiteSar extends SpireClient {

  private static final String VERSION_NO = "1.1";
  private static final String NAMESPACE = "SPIRE_CREATE_LITE_SAR";
  private static final String CHILD_NAME = "SAR_DETAILS";
  private final ObjectMapper objectMapper;

  @Inject
  public CreateLiteSar(@Named("createLiteSarUrl") String url,
                       @Named("soapUserName") String userName,
                       @Named("soapPassword") String password) {
    super(url, userName, password);
    this.objectMapper = new ObjectMapper();
  }

  /**
   * createLiteSar
   */
  public SOAPMessage createLiteSar(CustomerItem item) {
    SOAPMessage request = getRequest(NAMESPACE, CHILD_NAME);

    addChild(request, "VERSION_NO", VERSION_NO);
    addChild(request, "WUA_ID", item.getUserId());
    addChild(request, "CUSTOMER_NAME", item.getCustomerName());
    addChild(request, "CUSTOMER_TYPE", item.getCustomerType());
    addChild(request, "LITE_ADDRESS", getAddressItemJson(item.getAddressItem()));
    addChild(request, "ADDRESS", getFriendlyAddress(item.getAddressItem()));
    addChild(request, "COUNTRY_REF", item.getAddressItem().getCountry());
    addChild(request, "WEBSITE", item.getWebsite());

    String companiesHouseNumber = item.getCompaniesHouseNumber();
    if(!StringUtils.isBlank(companiesHouseNumber)) {
      addChild(request, "COMPANIES_HOUSE_NUMBER", companiesHouseNumber);
      addChild(request, "COMPANIES_HOUSE_VALIDATED", item.getCompaniesHouseValidated().toString());
    }
    String eoriNumber = item.getEoriNumber();
    if(!StringUtils.isBlank(eoriNumber)) {
      addChild(request, "EORI_NUMBER", eoriNumber);
      addChild(request, "EORI_VALIDATED", item.getEoriValidated().toString());
    }
    return getResponse(request);
  }

}