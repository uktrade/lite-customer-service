package uk.gov.bis.lite.sar.exception;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException(String companyName) {
    super("No Company Found Named: " + companyName);
  }

  public static class CompanyNotFoundExceptionHandler implements ExceptionMapper<CompanyNotFoundException> {
    @Override
    public Response toResponse(CompanyNotFoundException exception) {
      return Response.status(404).entity(new ErrorMessage(404, exception.getMessage())).build();
    }
  }
}
