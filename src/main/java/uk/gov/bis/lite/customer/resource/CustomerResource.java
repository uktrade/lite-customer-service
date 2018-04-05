package uk.gov.bis.lite.customer.resource;

import static uk.gov.bis.lite.customer.resource.ResourceUtil.validateUserIdToJwt;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.view.CustomerView;
import uk.gov.bis.lite.customer.service.CustomerService;
import uk.gov.bis.lite.customer.api.view.wrapper.CustomerWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  public CustomerView getCustomers(@NotNull @PathParam("customerId") String customerId, @Auth LiteJwtUser user) {
    CustomerView customer = null;
    Optional<CustomerView> optCustomer = customerService.getCustomerById(customerId);
    if (!optCustomer.isPresent()) {
      throwException("No Customer found.", Response.Status.NOT_FOUND);
    } else {
      customer = optCustomer.get();
    }
    return customer;
  }

  @GET
  @Path("/user-customers/user/{userId}")
  public List<CustomerView> getUserCustomers(@NotNull @PathParam("userId") String userId, @Auth LiteJwtUser user) {
    validateUserIdToJwt(userId, user);
    return customerService.getCustomersByUserId(userId);
  }

  @GET
  @Path("/search-customers/org-info")
  public List<CustomerView> getSearchCustomers(@QueryParam("postcode") String postcode,
                                               @QueryParam("eori") String eori,
                                               @Auth LiteJwtUser user) {
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
  public CustomerView getSearchCustomersByCompanyNumber(@PathParam("chNumber") String chNumber, @Auth LiteJwtUser user) {
    CustomerView customer = null;
    if (!StringUtils.isBlank(chNumber)) {
      List<CustomerView> customers = customerService.getCustomersByCompanyNumber(chNumber);
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

  //todo: add JWT validation for REGULATOR users
  @GET
  @Path("/search-customers")
  public List<CustomerView> getSearchCustomersByNameOrCompanyNumber(@QueryParam("term") String searchTerm, @Auth LiteJwtUser user) {
    if (!StringUtils.isBlank(searchTerm)) {
      List<CustomerView> customers1 = customerService.getCustomersByCompanyNumber(searchTerm);
      List<CustomerView> customers2 = customerService.getCustomersByName(searchTerm);

      return Stream.concat(customers1.stream(), customers2.stream())
          .map(CustomerWrapper::new)
          .distinct()
          .map(CustomerWrapper::unwrap)
          .collect(Collectors.toList());
    } else {
      throwException("Company name or number is mandatory.", Response.Status.BAD_REQUEST);
    }
    return Collections.emptyList();
  }

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }
}
