package uk.gov.bis.lite.sar.client;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPMessage;

public class CompanyClient extends SpireClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyClient.class);

  private static final String ENVELOPE_NAMESPACE = "SPIRE_COMPANIES";
  private static final String CHILD_NAME = "getCompanies";

  @Inject
  public CompanyClient(@Named("soapCompanyUrl") String soapUrl, @Named("soapUserName") String clientUserName,
                       @Named("soapPassword") String clientPassword) {
    super(soapUrl, clientUserName, clientPassword);
  }

  public SOAPMessage getCompanyByPostCodeEoriNumber(String postCode, String eoriNumber) {
    SOAPMessage soap = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(soap, "postCode", postCode);
    addChild(soap, "eoriNumber", eoriNumber);
    return getResponse(soap);
  }

  public SOAPMessage getCompanyByPostCode(String postCode) {
    SOAPMessage soap = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(soap, "postCode", postCode);
    return getResponse(soap);
  }

  public SOAPMessage getCompanyByUserId(String userId) {
    SOAPMessage soap = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(soap, "userId", userId);
    return getResponse(soap);
  }

  public SOAPMessage getCompanyBySarRef(String sarRef) {
    SOAPMessage soap = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(soap, "sarRef", sarRef);
    return getResponse(soap);
  }

  public SOAPMessage getCompanyByName(String name) {
    SOAPMessage soap = getSpirRequest(ENVELOPE_NAMESPACE, CHILD_NAME);
    addChild(soap, "companyName", name);
    return getResponse(soap);
  }
}
