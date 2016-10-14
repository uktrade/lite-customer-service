package uk.gov.bis.lite.sar.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class Util {

  public static boolean isBlank(String arg) {
    return StringUtils.isBlank(arg);
  }

  /**
   * Returns optional string, empty if blank
   */
  public static Optional<String> opt(String arg) {
    return StringUtils.isBlank(arg) ? Optional.empty() : Optional.of(arg);
  }
}
