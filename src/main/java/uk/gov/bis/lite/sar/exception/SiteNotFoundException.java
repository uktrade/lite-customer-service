package uk.gov.bis.lite.sar.exception;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SiteNotFoundException extends RuntimeException {
  public SiteNotFoundException(String siteName) {
    super("No SpireSite Found Named: " + siteName);
  }

  public static class SiteNotFoundExceptionHandler implements ExceptionMapper<SiteNotFoundException> {
    @Override
    public Response toResponse(SiteNotFoundException exception) {
      return Response.status(404).entity(new ErrorMessage(404, exception.getMessage())).build();
    }
  }
}
