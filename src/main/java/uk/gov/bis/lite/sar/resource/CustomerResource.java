package uk.gov.bis.lite.sar.resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.model.CustomerItem;
import uk.gov.bis.lite.sar.service.CustomerService;
import uk.gov.bis.lite.sar.util.Util;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);
  private CustomerService customerService;

  @Inject
  public CustomerResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/customer")
  public Response createCustomer(CustomerItem item) {
    Optional<String> sarRef = customerService.createCustomer(item);
    if (sarRef.isPresent()) {
      LOGGER.info("createCustomer goodResponse");
      return goodResponse(sarRef.get());
    }
    LOGGER.info("createCustomer badRequest");
    return badRequest("Could not create Customer");
  }

  @GET
  @Path("/customers/{customerId}")
  public List<Customer> getCustomers(@NotNull @PathParam("customerId") String customerId) {
    return customerService.getCustomersById(customerId);
  }

  @GET
  @Path("/user-customers/user/{userId}")
  public List<Customer> getUserCustomers(@NotNull @PathParam("userId") String userId) {
    return customerService.getCustomersByUserId(userId);
  }

  @GET
  @Path("/search-customers/org-info")
  public List<Customer> getSearchCustomers(@QueryParam("postcode") String postcode,
                                           @QueryParam("eori") String eori) {
    if (Util.isBlank(postcode)) {
      throw new WebApplicationException("postcode is a mandatory parameter", Response.Status.BAD_REQUEST);
    }
    if (Util.isBlank(eori)) {
      return customerService.getCustomersBySearch(postcode);
    } else {
      return customerService.getCustomersBySearch(postcode, eori);
    }
  }

  private Response badRequest(String message) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ImmutableMap.of("code", Response.Status.BAD_REQUEST, "message", message))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }
}
