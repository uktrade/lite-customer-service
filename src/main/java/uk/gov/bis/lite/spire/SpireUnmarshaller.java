package uk.gov.bis.lite.spire;

import uk.gov.bis.lite.spire.unmarshaller.SingleResponseElement;

import javax.xml.soap.SOAPMessage;

public class SpireUnmarshaller {

  private SingleResponseElement singleResponseElement;

  public SpireUnmarshaller() {
    this.singleResponseElement = new SingleResponseElement();
  }

  public String getSingleResponseElementContent(SpireResponse spireResponse) {
    String ref = "";
    SOAPMessage message = spireResponse.getMessage();
    switch (spireResponse.getEndpoint()) {
      case CREATE_SITE_FOR_SAR:
        ref = singleResponseElement.getSpireResponse(message, SpireName.CSFS_RESPONSE_ELEMENT);
        break;
      case CREATE_LITE_SAR:
        ref = singleResponseElement.getSpireResponse(message, SpireName.CLS_RESPONSE_ELEMENT);
        break;
      case EDIT_USER_ROLES:
        ref = singleResponseElement.getSpireResponse(message, SpireName.EUR_RESPONSE_ELEMENT);
        break;
      case CREATE_OGEL_APP:
        ref = singleResponseElement.getSpireResponse(message, SpireName.COA_RESPONSE_ELEMENT);
        break;
      default:
    }
    return ref;
  }


}
