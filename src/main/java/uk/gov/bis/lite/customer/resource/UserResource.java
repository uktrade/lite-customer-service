package uk.gov.bis.lite.customer.resource;

import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.customer.api.UsersResponse;
import uk.gov.bis.lite.customer.api.param.UserRoleParam;
import uk.gov.bis.lite.customer.service.UserService;
import uk.gov.bis.lite.customer.service.UserServiceImpl;

import java.util.Optional;

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
import javax.ws.rs.core.Response.Status;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private final UserService userService;

  @Inject
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/customer-admins/{customerId}")
  public UsersResponse getAdministratorUserDetails(@NotNull @PathParam("customerId") String customerId,
                                                   @Auth LiteJwtUser user) {
    Optional<UsersResponse> optionalUsers = userService.getCustomerAdminUsers(customerId);
    if (!optionalUsers.isPresent()) {
      throw new WebApplicationException("No customer admins.", Status.NOT_FOUND);
    } else {
      return optionalUsers.get();
    }
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Path("/user-roles/user/{userId}/site/{siteRef}")
  public Response userRole(@NotNull @PathParam("userId") String userId,
                           @NotNull @PathParam("siteRef") String siteRef,
                           UserRoleParam param,
                           @Auth LiteJwtUser liteJwtUser) {
    String completed = userService.userRoleUpdate(param, userId, siteRef);
    if (UserServiceImpl.USER_ROLE_UPDATE_STATUS_COMPLETE.equals(completed)) {
      return Response.ok().build();
    } else {
      throw new WebApplicationException("Could not update userRole", Status.BAD_REQUEST);
    }
  }
}