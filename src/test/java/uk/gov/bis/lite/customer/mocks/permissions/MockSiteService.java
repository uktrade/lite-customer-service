package uk.gov.bis.lite.customer.mocks.permissions;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class MockSiteService implements SiteService {

  private List<SiteView> mockUserCustomerSites = Collections.emptyList();

  private ObjectMapper mapper = new ObjectMapper();
  private boolean failCreateSite;
  private boolean missingSite;

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    if (failCreateSite) {
      return Optional.empty();
    } else {
      return Optional.of(getSiteViewMock());
    }
  }

  public List<SiteView> getSites(String customerId, String userId) {
    return mockUserCustomerSites;
  }

  public List<SiteView> getSites(String customerId) {
    return mockUserCustomerSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    if (missingSite) {
      return Optional.empty();
    } else {
      return Optional.of(getSiteViewMock());
    }
  }

  /**
   * Pact State setters
   */
  public void setFailCreateSite(boolean failCreateSite) {
    this.failCreateSite = failCreateSite;
  }

  public void setMissingSite(boolean missingSite) {
    this.missingSite = missingSite;
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

  public void setSitesExistForUserAndCustomer(boolean hasSite) {
    if (hasSite) {
      mockUserCustomerSites = Collections.singletonList(getSiteViewMock());
    } else {
      mockUserCustomerSites = Collections.emptyList();
    }

  }
}
