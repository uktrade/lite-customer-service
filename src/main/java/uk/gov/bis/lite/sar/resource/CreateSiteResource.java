package uk.gov.bis.lite.sar.resource;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.sar.model.SiteItem;
import uk.gov.bis.lite.sar.service.SiteService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CreateSiteResource {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateSiteResource.class);
  private SiteService siteService;

  @Inject
  public CreateSiteResource(SiteService siteService) {
    this.siteService = siteService;
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/create-site")
  public Response createSite(SiteItem item) {
    String siteRef = siteService.createSite(item);
    return goodResponse(siteRef);
  }

  private Response goodResponse(String value) {
    return Response.ok("{\"response\": \"" + value + "\"}", MediaType.APPLICATION_JSON).build();
  }
}
