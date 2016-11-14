package uk.gov.bis.lite.sar.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.item.AddressItem;

public class Util {

  private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

  private static ObjectMapper mapper = new ObjectMapper();

  public static String getFriendlyAddress(AddressItem address) {
    return Joiner.on("\n").skipNulls()
        .join(address.getLine1(), address.getLine2(), address.getTown(),
            address.getPostcode(), address.getCounty(), address.getCountry());
  }

  public static String getAddressItemJson(AddressItem address) {
    String json = "";
    try {
      json = mapper.writeValueAsString(address).trim();
    } catch (JsonProcessingException e) {
      throw new RuntimeException("JsonProcessingException on AddressItem", e);

    }
    return json;
  }
}
