package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.item.in.CustomerIn;
import uk.gov.bis.lite.customer.service.CustomerService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class CustomerCreateResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreateResource.class);
  private CustomerService customerService;

  @Inject
  public CustomerCreateResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/create-customer")
  public Response createCustomer(CustomerIn item) {

    return goodResponse(customerService.createCustomer(item));
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }
}
