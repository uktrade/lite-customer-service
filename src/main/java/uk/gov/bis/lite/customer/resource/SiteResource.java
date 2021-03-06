package uk.gov.bis.lite.customer.resource;

import static uk.gov.bis.lite.customer.resource.ResourceUtil.validateUserIdToJwt;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class SiteResource {

  private final SiteService siteService;

  @Inject
  public SiteResource(SiteService siteService) {
    this.siteService = siteService;
  }

  @GET
  @Path("/user-sites/customer/{customerId}/user/{userId}")
  public List<SiteView> getSites(@NotNull @PathParam("customerId") String customerId,
                                 @NotNull @PathParam("userId") String userId,
                                 @Auth LiteJwtUser user) {
    validateUserIdToJwt(userId, user);
    return siteService.getSitesByUserId(customerId, userId);
  }

  @GET
  @Path("/sites/{siteId}")
  public SiteView getSite(@NotNull @PathParam("siteId") String siteId, @Auth LiteJwtUser user) {
    SiteView out = null;
    Optional<SiteView> optSite = siteService.getSite(siteId);
    if (!optSite.isPresent()) {
      throwException("No Site found.", Response.Status.NOT_FOUND);
    } else {
      out = optSite.get();
    }
    return out;
  }

  private void throwException(String message, Response.Status status) {
    throw new WebApplicationException(message, status);
  }
}
