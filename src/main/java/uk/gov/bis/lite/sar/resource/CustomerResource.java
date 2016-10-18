package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Customer;
import uk.gov.bis.lite.sar.service.CustomerService;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
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
    if (StringUtils.isBlank(postcode)) {
      throw new WebApplicationException("postcode is a mandatory parameter", Response.Status.BAD_REQUEST);
    }
    if (StringUtils.isBlank(eori)) {
      return customerService.getCustomersBySearch(postcode);
    } else {
      return customerService.getCustomersBySearch(postcode, eori);
    }
  }
}
