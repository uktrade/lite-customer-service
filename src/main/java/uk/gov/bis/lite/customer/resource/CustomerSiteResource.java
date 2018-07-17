package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

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
public class CustomerSiteResource {

  private final SiteService siteService;

  @Inject
  public CustomerSiteResource(SiteService siteService) {
    this.siteService = siteService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/customer-sites/{customerId}")
  public SiteView createSite(SiteParam param,
                             @NotNull @PathParam("customerId") String customerId,
                             @NotNull @QueryParam("userId") String userId,
                             @Auth LiteJwtUser user) {
    SiteView siteView = null;
    Optional<SiteView> optSiteView = siteService.createSite(param, customerId, userId);
    if (!optSiteView.isPresent()) {
      throwException("Site not created.", Response.Status.BAD_REQUEST);
    } else {
      siteView = optSiteView.get();
    }
    return siteView;
  }

  //todo: add JWT validation for REGULATOR users
  @GET
  @Path("/customer-sites/{customerId}")
  public List<SiteView> getCustomerSites(@NotNull @PathParam("customerId") String customerId, @Auth LiteJwtUser user) {
    return siteService.getSitesByCustomerId(customerId);
  }

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }
}
