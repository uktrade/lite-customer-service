package uk.gov.bis.lite.customer.exception;

import javax.ws.rs.WebApplicationException;

public class SpireForbiddenException extends WebApplicationException {

  /**
   * SpireForbiddenException
   *
   * @param info information on exception
   */
  public SpireForbiddenException(String info) {
    super("Spire Forbidden Exception: " + info, 403);
  }
}
