package uk.gov.bis.lite.customer.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.param.AddressParam;

public class Util {

  private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

  private static ObjectMapper mapper = new ObjectMapper();

  public static String getBooleanValue(Boolean arg) {
    return arg == null ? "false" : arg ? "true" : "false";
  }

  public static String getFriendlyAddress(AddressParam addressParam) {
    return Joiner.on("\n").skipNulls()
        .join(addressParam.getLine1(), addressParam.getLine2(), addressParam.getTown(),
            addressParam.getPostcode(), addressParam.getCounty(), addressParam.getCountry());
  }

  public static String getAddressItemJson(AddressParam addressParam) {
    String json = "";
    try {
      json = mapper.writeValueAsString(addressParam).trim();
    } catch (JsonProcessingException e) {
      throw new RuntimeException("JsonProcessingException on AddressParam", e);

    }
    return json;
  }
}
