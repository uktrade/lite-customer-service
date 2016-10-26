package uk.gov.bis.lite.spire;


import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SpireRequest {


  private String endpointTarget;private SpireEndpoint endpoint;

  private SOAPMessage message;
  private SOAPElement parent;

  public SpireRequest(SpireEndpoint endpoint) {
    this.endpoint = endpoint;
  }

  public void setSoapMessage(SOAPMessage message) {
    this.message = message;
    try {
      this.parent = (SOAPElement) message.getSOAPPart().getEnvelope().getBody().getChildElements().next();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
  }

  public void addChild(String childName, String childText) {
    try {
      SOAPElement child = parent.addChildElement(childName);
      child.addTextNode(childText);
      message.saveChanges();
    } catch (SOAPException e) {
      throw new RuntimeException("An error occurred adding child element", e);
    }
  }

  public SpireEndpoint getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(SpireEndpoint endpoint) {
    this.endpoint = endpoint;
  }

  public SOAPMessage getSoapMessage() {
    return message;
  }

  public String getEndpointTarget() {
    return endpointTarget;
  }

  public void setEndpointTarget(String endpointTarget) {
    this.endpointTarget = endpointTarget;
  }
}
