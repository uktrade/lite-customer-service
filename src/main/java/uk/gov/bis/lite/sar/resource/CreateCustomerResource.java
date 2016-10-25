package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.service.CustomerService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class CreateCustomerResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateCustomerResource.class);
  private CustomerService customerService;

  @Inject
  public CreateCustomerResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/create-customer")
  public Response createCustomer(CustomerItem item) {
    LOGGER.info("createCustomer");
    String sarRef = customerService.createCustomer(item);
    return goodResponse(sarRef);
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }
}
