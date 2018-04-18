package uk.gov.bis.lite.customer.spire;

import uk.gov.bis.lite.common.spire.client.errorhandler.ErrorNodeErrorHandler;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.customer.exception.SpireForbiddenException;

public class SpireErrorNodeErrorHandler extends ErrorNodeErrorHandler {

  public SpireErrorNodeErrorHandler() {
  }

  public void handleError(String errorText) {
    if (errorText != null && errorText.contains("USER_LACKS_PRIVILEGES")) {
      throw new SpireForbiddenException("USER_LACKS_PRIVILEGES");
    } else {
      throw new SpireClientException("ERROR: [" + errorText + "]");
    }
  }

}
