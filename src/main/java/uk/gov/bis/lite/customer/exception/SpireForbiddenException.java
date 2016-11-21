package uk.gov.bis.lite.customer.exception;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SpireForbiddenException extends RuntimeException {

  /**
   * SpireForbiddenException
   *
   * @param info information on exception
   */
  public SpireForbiddenException(String info) {
    super("Spire Forbidden Exception: " + info);
  }

  /**
   * Provided for Dropwizard/Jersey integration
   */
  public static class ServiceExceptionMapper implements ExceptionMapper<SpireForbiddenException> {

    @Override
    public Response toResponse(SpireForbiddenException exception) {
      return Response.status(403).entity(new ErrorMessage(403, exception.getMessage())).build();
    }
  }
}
