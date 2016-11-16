package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteServiceMock implements SiteService {

  private List<SiteView> mockSites = new ArrayList<>();
  private Optional<SiteView> mockSiteView;

  public SiteServiceMock(String mockSiteId, int numberOfSites) {
    initSites(mockSiteId, numberOfSites);
  }

  private void initSites(String mockSiteId, int numberOfSites) {

    SiteView siteView = new SiteView();
    siteView.setSiteId(mockSiteId);
    mockSiteView = Optional.of(siteView);

    for (int i = 0; i < numberOfSites; i++) {
      SiteView site = new SiteView();
      site.setCustomerId("site" + i);
      mockSites.add(site);
    }
  }

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    return mockSiteView;
  }

  public List<SiteView> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    return mockSiteView;
  }


}
