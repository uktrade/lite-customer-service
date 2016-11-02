package uk.gov.bis.lite.sar.resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Users;
import uk.gov.bis.lite.sar.model.item.UserRoleItem;
import uk.gov.bis.lite.sar.service.UserService;

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
public class UserResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
  private UserService userService;

  @Inject
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Path("/customer-admins/{customerId}")
  public Users getAdministratorUserDetails(@NotNull @PathParam("customerId") String customerId) {
    return userService.getCustomerAdminUsers(customerId);
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/user-roles/user/{userId}/site/{siteRef}")
  public Response userRole(@NotNull @PathParam("userId") String userId,
                           @NotNull @PathParam("siteRef") String siteRef,
                           UserRoleItem item) {
    String completed = userService.userRoleUpdate(item, userId, siteRef);
    if (completed.equals(UserService.USER_ROLE_UPDATE_STATUS_COMPLETE)) {
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