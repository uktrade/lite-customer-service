package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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
public class SiteCreateResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteCreateResource.class);
  private SiteService siteService;

  @Inject
  public SiteCreateResource(SiteService siteService) {
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

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }
}
