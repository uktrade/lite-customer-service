package uk.gov.bis.lite.sar.exception;


import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CreateException extends RuntimeException {

  public CreateException(String info) {
    super("Create Exception: " + info);
  }

  public static class ServiceExceptionMapper implements ExceptionMapper<CreateException> {
    @Override
    public Response toResponse(CreateException exception) {
      return Response.status(400).entity(new ErrorMessage(400, exception.getMessage())).build();
    }
  }
}
