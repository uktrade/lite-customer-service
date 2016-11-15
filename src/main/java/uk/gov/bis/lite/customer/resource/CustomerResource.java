package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.item.out.CustomerOut;
import uk.gov.bis.lite.customer.service.CustomerService;

import java.util.List;
import java.util.Optional;

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
  public CustomerOut getCustomers(@NotNull @PathParam("customerId") String customerId) {
    CustomerOut customer = null;
    Optional<CustomerOut> optCustomer = customerService.getCustomerById(customerId);
    if (!optCustomer.isPresent()) {
      throwException("No Customer found.", Response.Status.NOT_FOUND);
    } else {
      customer = optCustomer.get();
    }
    return customer;
  }

  @GET
  @Path("/user-customers/user/{userId}")
  public List<CustomerOut> getUserCustomers(@NotNull @PathParam("userId") String userId) {
    return customerService.getCustomersByUserId(userId);
  }

  @GET
  @Path("/search-customers/org-info")
  public List<CustomerOut> getSearchCustomers(@QueryParam("postcode") String postcode,
                                              @QueryParam("eori") String eori) {
    if (StringUtils.isBlank(postcode)) {
      throwException("postcode is a mandatory parameter", Response.Status.BAD_REQUEST);
    }
    if (StringUtils.isBlank(eori)) {
      return customerService.getCustomersBySearch(postcode);
    } else {
      return customerService.getCustomersBySearch(postcode, eori);
    }
  }

  @GET
  @Path("/search-customers/registered-number/{chNumber}")
  public CustomerOut getSearchCustomersByCompanyNumber(@PathParam("chNumber") String chNumber) {
    CustomerOut customer = null;
    if (!StringUtils.isBlank(chNumber)) {
      List<CustomerOut> customers = customerService.getCustomersByCompanyNumber(chNumber);
      if (customers.size() == 1) {
        customer = customers.get(0);
      } else if (customers.size() == 0) {
        throwException("No Customer found for company number: " + chNumber, Response.Status.NOT_FOUND);
      } else {
        throwException("Multiple Customers found for company number: " + chNumber, Response.Status.INTERNAL_SERVER_ERROR);
      }
    } else {
      throwException("Company Number blank. No Customer found.", Response.Status.NOT_FOUND);
    }
    return customer;
  }

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }
}
