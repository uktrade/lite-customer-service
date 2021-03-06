package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.param.CustomerParam;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class CustomerCreateResource {

  private final CustomerService customerService;

  @Inject
  public CustomerCreateResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/create-customer")
  public CustomerView createCustomer(CustomerParam param, @Auth LiteJwtUser liteJwtUser) {
    CustomerView customer = null;
    Optional<CustomerView> optCustomer = customerService.createCustomer(param);
    if (!optCustomer.isPresent()) {
      throwException("Customer not created.", Response.Status.BAD_REQUEST);
    } else {
      customer = optCustomer.get();
    }
    return customer;
  }

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }

}
