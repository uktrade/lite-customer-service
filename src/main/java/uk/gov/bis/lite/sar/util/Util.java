package uk.gov.bis.lite.sar.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.sar.model.item.AddressItem;

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

  public static boolean addChild(SpireRequest request, String elementName, String elementContent) {
    boolean added = false;
    if(!StringUtils.isBlank(elementContent)) {
      request.addChild(elementName, elementContent);
      added = true;
    } else {
      LOGGER.warn("No content found for element: " + elementName + " - not including this element in Spire request");
    }
    return added;
  }
}
