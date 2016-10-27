package uk.gov.bis.lite.sar.resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.UserRoleItem;
import uk.gov.bis.lite.sar.service.UserRoleService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class UserRoleResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleResource.class);
  private UserRoleService userRoleService;

  @Inject
  public UserRoleResource(UserRoleService userRoleService) {
    this.userRoleService = userRoleService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/userRole")
  public Response userRole(UserRoleItem item) {
    String completed = userRoleService.userRoleUpdate(item);
    if (completed.equals(UserRoleService.USER_ROLE_UPDATE_STATUS_COMPLETE)) {
      return goodResponse(completed);
    }
    return badRequest("Could not update userRole");
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