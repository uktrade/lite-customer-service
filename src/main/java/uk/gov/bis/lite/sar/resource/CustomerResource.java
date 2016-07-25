package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import uk.gov.bis.lite.sar.model.Company;
import uk.gov.bis.lite.sar.model.CompanyWrapper;
import uk.gov.bis.lite.sar.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sar")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

  private CustomerService customerService;

  @Inject
  public CustomerResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GET
  @Path("{companyName}")
  public List<CompanyWrapper> getCustomerInformation(@NotNull @PathParam("companyName") String name) {
    List<Company> companyList = customerService.getCustomerInformation(name);
    return companyList.stream().map(c -> new CompanyWrapper(c)).collect(Collectors.toList());
  }
}
