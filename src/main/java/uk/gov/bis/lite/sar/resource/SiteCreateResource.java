package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.item.in.SiteIn;
import uk.gov.bis.lite.sar.service.SiteService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
  public Response createSite(SiteIn siteItemIn, @NotNull @PathParam("customerId") String customerId,
                             @NotNull @QueryParam("userId") String userId) {
    return goodResponse(siteService.createSite(siteItemIn, customerId, userId));
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }
}
