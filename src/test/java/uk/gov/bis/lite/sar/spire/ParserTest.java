package uk.gov.bis.lite.sar.spire;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import uk.gov.bis.lite.common.spire.client.SpireResponse;
import uk.gov.bis.lite.sar.mocks.CustomerServiceMock;
import uk.gov.bis.lite.sar.resource.CustomerCreateResource;
import uk.gov.bis.lite.sar.resource.CustomerResource;
import uk.gov.bis.lite.sar.spire.parsers.CompanyParser;
import uk.gov.bis.lite.sar.util.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Testing Spire Response parsing
 */
public class ParserTest {

  private final int OK = Response.Status.OK.getStatusCode();

  // CustomerServiceMock setup
  private static final int MOCK_CUSTOMERS_NUMBER = 1;
  private static final String MOCK_CUSTOMERS_SAR_REF_TAG = "REF";
  private static final String MOCK_CUSTOMER_ID = "id1";
  private static final CustomerServiceMock mockService = new CustomerServiceMock(MOCK_CUSTOMER_ID, MOCK_CUSTOMERS_NUMBER, MOCK_CUSTOMERS_SAR_REF_TAG);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new CustomerResource(mockService))
      .addResource(new CustomerCreateResource(mockService)).build();

  @Test
  public void testParser() {
    CompanyParser companyParser = new CompanyParser();

    String companyEnvelope = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.fivium.co.uk/fox/webservices/ispire/SPIRE_COMPANIES\">\n" +
        "  <soap:Header/>\n" +
        "  <soap:Body>\n" +
        "    <ns:getCompaniesResponse>\n" +
        "      <COMPANIES_LIST>\n" +
        "        <COMPANY>\n" +
        "          <SAR_REF>SAR17463</SAR_REF>\n" +
        "          <COMPANY_NAME>ERXQ1</COMPANY_NAME>\n" +
        "          <SHORT_NAME>ERX487290695</SHORT_NAME>\n" +
        "          <ORGANISATION_TYPE>O</ORGANISATION_TYPE>\n" +
        "          <ORG_TYPE_FULL>Other</ORG_TYPE_FULL>\n" +
        "          <COMPANY_NUMBER>GBX006</COMPANY_NUMBER>\n" +
        "          <REGISTERED_ADDRESS>QUARRY FARM BUSINESS\n" +
        "UNITS\n" +
        "REDHILL\n" +
        "BRISTOL\n" +
        "BS40 5TU\n" +
        "GREATER LONDON\n" +
        "CTRY0</REGISTERED_ADDRESS>\n" +
        "          <REGISTRATION_STATUS>REGISTERED</REGISTRATION_STATUS>\n" +
        "          <APPLICANT_TYPE>LITE</APPLICANT_TYPE>\n" +
        "          <COUNTRY_OF_ORIGIN>0</COUNTRY_OF_ORIGIN>\n" +
        "        </COMPANY>\n" +
        "      </COMPANIES_LIST>\n" +
        "    </ns:getCompaniesResponse>\n" +
        "  </soap:Body>\n" +
        "</soap:Envelope>\n";

    SOAPMessage message = getSoapMessage(companyEnvelope) ;

   SpireResponse spireResponse = new SpireResponse(message);



    //assertThat(extractor.extractIdFrom(m), equalTo(expectedMessageId));
  }

  /**
   * private methods
   */
  private SOAPMessage getSoapMessage(String xml) {
    SOAPMessage message = null;
    try {
      message = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(xml.getBytes()));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SOAPException e) {
      e.printStackTrace();
    }
    return message;
  }

  private Invocation.Builder request(String url, String mediaType) {
    return target(url).request(mediaType);
  }

  private Invocation.Builder request(String url) {
    return target(url).request();
  }

  private WebTarget target(String url) {
    return resources.client().target(url);
  }

  private int status(Response response) {
    return response.getStatus();
  }

}

