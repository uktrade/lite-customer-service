package uk.gov.bis.lite.customer.spire;

import uk.gov.bis.lite.common.spire.client.errorhandler.DefaultErrorHandler;
import uk.gov.bis.lite.customer.exception.SpireForbiddenException;

public class SiteErrorHandler extends DefaultErrorHandler {

  public SiteErrorHandler() {}

  public void mapErrorText(String errorText) {
    if (errorText != null && errorText.contains("USER_LACKS_PRIVILEGES")) {
      throw new SpireForbiddenException("USER_LACKS_PRIVILEGES");
    } else {
      super.mapErrorText(errorText);
    }
  }

}
