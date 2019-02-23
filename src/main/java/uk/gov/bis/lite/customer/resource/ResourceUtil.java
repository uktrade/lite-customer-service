package uk.gov.bis.lite.customer.resource;

import org.apache.commons.lang3.StringUtils;
import uk.gov.bis.lite.common.jwt.LiteJwtUser;
import uk.gov.bis.lite.user.api.view.enums.AccountType;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResourceUtil {
  private ResourceUtil() {
  }

  /**
   * Validates the given userId against the id stored in {@link LiteJwtUser}. Throws {@link WebApplicationException} with
   *  {@link Response.Status#UNAUTHORIZED} when the pair do not match.
   * @param userId the userId to validate
   * @param user the {@Link LiteJwtUser} principle to validate against
   */
  /**
   * LITE-1028: allow regulator users to view all customers and sites for a user
   */
  static void validateUserIdToJwt(String userId, LiteJwtUser user) {
    if (user.getAccountType() != AccountType.REGULATOR) {
      if (!StringUtils.equals(userId, user.getUserId())) {
        throw new WebApplicationException(String.format("userId %s does not match value supplied in token (%s)",
            userId, user.getUserId()), Response.Status.UNAUTHORIZED);
      }
    }
  }
}
