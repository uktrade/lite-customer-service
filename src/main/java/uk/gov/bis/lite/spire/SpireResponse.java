package uk.gov.bis.lite.spire;

import javax.xml.soap.SOAPMessage;

public class SpireResponse {

  private SpireEndpoint endpoint;
  private SOAPMessage message;


  public SpireResponse(SOAPMessage message) {
    this.message = message;
  }

  public SpireResponse(SOAPMessage message, SpireEndpoint endpoint) {
    this.message = message;
    this.endpoint = endpoint;
  }

  public SOAPMessage getMessage() {
    return message;
  }

  public void setMessage(SOAPMessage message) {
    this.message = message;
  }

  public SpireEndpoint getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(SpireEndpoint endpoint) {
    this.endpoint = endpoint;
  }
}
