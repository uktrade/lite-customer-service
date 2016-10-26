package uk.gov.bis.lite.spire;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SpireException extends RuntimeException {

  public SpireException(String info) {
    super("Spire Exception: " + info);
  }

  public static class ServiceExceptionMapper implements ExceptionMapper<uk.gov.bis.lite.spireclient.spire.SpireException> {
    @Override
    public Response toResponse(uk.gov.bis.lite.spireclient.spire.SpireException exception) {
      return Response.status(400).entity(new ErrorMessage(400, exception.getMessage())).build();
    }
  }
}
