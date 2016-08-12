package uk.gov.bis.lite.sar.client;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class CustomerServiceClient extends ServiceClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceClient.class);
  private String soapUrl;
  private String soapClientUserName;
  private String soapClientPassword;

  @Inject
  public CustomerServiceClient(@Named("soapCompanyUrl") String soapUrl, @Named("soapUserName") String clientUserName,
                               @Named("soapPassword") String clientPassword) {
    this.soapUrl = soapUrl;
    this.soapClientUserName = clientUserName;
    this.soapClientPassword = clientPassword;
  }

  public SOAPMessage executeRequest(String companyName) {

    SOAPConnectionFactory soapConnectionFactory;
    SOAPConnection soapConnection = null;
    try {
      soapConnectionFactory = SOAPConnectionFactory.newInstance();
      soapConnection = soapConnectionFactory.createConnection();

      SOAPMessage request = createRequest(companyName);
      LOGGER.debug(messageAsString(request));
      LOGGER.info("Retrieving Companies List from Spire");
      final Stopwatch stopwatch = Stopwatch.createStarted();
      SOAPMessage response = soapConnection.call(request, soapUrl);
      stopwatch.stop();
      LOGGER.info("New Companies list has been retrieved from Spire in " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds ");
      LOGGER.debug(messageAsString(response));
      return response;
    } catch (SOAPException e) {
      throw new RuntimeException("An error occurred establishing the connection with SOAP client", e);
    } finally {
      if (soapConnection != null) {
        try {
          LOGGER.info("Closing the soap connection for Company info request ");
          soapConnection.close();
        } catch (SOAPException e) {
          LOGGER.error("An error occurred closing the SOAP Company connection. ", e);
        }
      }
    }
  }

  private SOAPMessage createRequest(String name) {

    MessageFactory messageFactory;
    try {
      messageFactory = MessageFactory.newInstance();

      SOAPMessage soapMessage = messageFactory.createMessage();
      SOAPPart soapPart = soapMessage.getSOAPPart();

      // SOAP Envelope
      SOAPEnvelope envelope = soapPart.getEnvelope();
      envelope.addNamespaceDeclaration("spir", "http://www.fivium.co.uk/fox/webservices/ispire/SPIRE_COMPANIES");

      // SOAP Body
      SOAPBody soapBody = envelope.getBody();
      final SOAPElement soapBodyElement = soapBody.addChildElement("getCompanies", "spir");
      SOAPElement soapBodyElem1 = soapBodyElement.addChildElement("companyName");
      soapBodyElem1.addTextNode(name);

      MimeHeaders headers = soapMessage.getMimeHeaders();
      String authorization = Base64.getEncoder().encodeToString((soapClientUserName + ":" + soapClientPassword).getBytes("utf-8"));
      headers.addHeader("Authorization", "Basic " + authorization);
      soapMessage.saveChanges();

      return soapMessage;
    } catch (SOAPException e) {
      throw new RuntimeException("An error occurred creating the SOAP request for retrieving Customer Information from Spire", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unsupported Encoding type", e);
    }
  }
}
