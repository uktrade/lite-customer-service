package uk.gov.bis.lite.customer.resource;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/customer-admins/{customerId}")
  public UsersResponse getAdministratorUserDetails(@NotNull @PathParam("customerId") String customerId) {
    UsersResponse out = null;
    Optional<UsersResponse> optionalUsers = userService.getCustomerAdminUsers(customerId);
    if (!optionalUsers.isPresent()) {
      throw new WebApplicationException("No customer admins.", Response.Status.NOT_FOUND);
    } else {
      return optionalUsers.get();
    }
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/user-roles/user/{userId}/site/{siteRef}")
  public Response userRole(@NotNull @PathParam("userId") String userId,
                           @NotNull @PathParam("siteRef") String siteRef,
                           UserRoleParam param) {
    String completed = userService.userRoleUpdate(param, userId, siteRef);
    if (completed.equals(UserServiceImpl.USER_ROLE_UPDATE_STATUS_COMPLETE)) {
      return Response.ok().build();
    }
    return badRequest("Could not update userRole");
  }

  private Response badRequest(String message) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ImmutableMap.of("code", Response.Status.BAD_REQUEST, "message", message))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}