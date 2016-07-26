package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import uk.gov.bis.lite.sar.model.Company;
import uk.gov.bis.lite.sar.model.CompanyWrapper;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.SiteWrapper;
import uk.gov.bis.lite.sar.service.CustomerService;
import uk.gov.bis.lite.sar.service.SiteService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

  private CustomerService customerService;
  private SiteService siteService;

  @Inject
  public CustomerResource(CustomerService customerService, SiteService siteService) {
    this.customerService = customerService;
    this.siteService = siteService;
  }

  @GET
  @Path("/sar/{companyName}")
  public List<CompanyWrapper> getCustomerInformation(@NotNull @PathParam("companyName") String name) {
    List<Company> companyList = customerService.getCustomerInformation(name);
    return companyList.stream().map(CompanyWrapper::new).collect(Collectors.toList());
  }

  @GET
  @Path("/user-sites/{sarId}")
  public List<SiteWrapper> getSiteInformation(@NotNull @PathParam("sarId") String sarId) {
    List<Site> userSiteList = siteService.getCustomerInformation(sarId);
    return userSiteList.stream().map(SiteWrapper::new).collect(Collectors.toList());
  }
}
