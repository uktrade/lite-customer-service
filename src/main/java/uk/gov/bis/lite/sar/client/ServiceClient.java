package uk.gov.bis.lite.sar.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public abstract class ServiceClient {

  public abstract SOAPMessage executeRequest(String reference);

  protected static String messageAsString(SOAPMessage soapMessage) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      soapMessage.writeTo(outputStream);
      return outputStream.toString();
    } catch (IOException | SOAPException e) {
      return null;
    }
  }
}
