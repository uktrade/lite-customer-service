package uk.gov.bis.lite.sar.resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.sar.service.SiteService;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/userRole")
  public Response userRole(UserRoleItem item) {
    String completed = siteService.userRoleUpdate(item);
    if (completed.equals(SiteService.USER_ROLE_UPDATE_STATUS_COMPLETE)) {
      LOGGER.info("userRole goodResponse");
      return goodResponse(completed);
    }
    LOGGER.info("userRole badRequest");
    return badRequest("Could not update site access");
  }

  @GET
  @Path("/user-sites/customer/{customerId}/user/{userId}")
  public List<Site> getSites(@NotNull @PathParam("customerId") String customerId,
                             @NotNull @PathParam("userId") String userId) {
    return siteService.getSites(customerId, userId);
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }

  private Response badRequest(String message) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ImmutableMap.of("code", Response.Status.BAD_REQUEST, "message", message))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
