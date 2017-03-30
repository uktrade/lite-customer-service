package uk.gov.bis.lite.customer.mocks.permissions;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class MockSiteService implements SiteService {

  private List<SiteView> mockSites = new ArrayList<>();

  private ObjectMapper mapper = new ObjectMapper();
  private boolean failCreateSite = false;

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    if (failCreateSite) {
      return Optional.empty();
    } else {
      return Optional.of(getSiteViewMock());
    }
  }

  public List<SiteView> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    return Optional.of(getSiteViewMock());
  }

  /**
   * Pact State setters
   */
  public void setFailCreateSite(boolean failCreateSite) {
    this.failCreateSite = failCreateSite;
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
