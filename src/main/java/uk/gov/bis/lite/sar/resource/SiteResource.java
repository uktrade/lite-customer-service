package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.service.SiteService;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
  @Path("/user-sites/customer/{customerId}/user/{userId}")
  public List<Site> getSites(@NotNull @PathParam("customerId") String customerId,
                             @NotNull @PathParam("userId") String userId) {
    return siteService.getSites(customerId, userId);
  }
}
