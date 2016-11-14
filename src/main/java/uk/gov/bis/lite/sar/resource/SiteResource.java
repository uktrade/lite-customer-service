package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.item.out.SiteOut;
import uk.gov.bis.lite.sar.service.SiteService;

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
public class SiteResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteResource.class);
  private SiteService siteService;

  @Inject
  public SiteResource(SiteService siteService) {
    this.siteService = siteService;
  }

  @GET
  @Path("/user-sites/customer/{customerId}")
  public List<SiteOut> getSites(@NotNull @PathParam("customerId") String customerId,
                                @NotNull @QueryParam("userId") String userId) {
    return siteService.getSites(customerId, userId);
  }

  @GET
  @Path("/sites/{siteId}")
  public SiteOut getSite(@NotNull @PathParam("siteId") String siteId) {
    SiteOut out = null;
    Optional<SiteOut> optSite = siteService.getSite(siteId);
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
