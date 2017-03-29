package uk.gov.bis.lite.customer.mocks.permissions;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockSiteService implements SiteService, PermissionsPactConstants {

  private List<SiteView> mockSites = new ArrayList<>();

  private ObjectMapper mapper = new ObjectMapper();

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    if(StringUtils.isBlank(userId)) {
      return Optional.empty();
    }
    return Optional.of(getSiteViewMock());
  }

  public List<SiteView> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    return Optional.of(getSiteViewMock());
  }

  private SiteView getSiteViewMock() {
    SiteView view = null;
    try {
      view = mapper.readValue(fixture("fixture/pact/mockSiteView.json"), SiteView.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return view;
  }


}
