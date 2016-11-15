package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteServiceMock implements SiteService {

  private SiteView mockSite = null;
  private List<SiteView> mockSites = new ArrayList<>();
  private String mockSiteId = "id1";

  public SiteServiceMock(String mockSiteId, int numberOfSites) {
    this.mockSiteId = mockSiteId;
    initSites(numberOfSites);
  }

  private void initSites(int numberOfSites) {

    mockSite = new SiteView();
    mockSite.setSiteId(mockSiteId);
    for (int i = 0; i < numberOfSites; i++) {
      SiteView site = new SiteView();
      site.setCustomerId("site" + i);
      mockSites.add(site);
    }
  }

  public String createSite(SiteParam siteItemIn, String customerId, String userId) {
    return mockSiteId;
  }


  public List<SiteView> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    return Optional.of(mockSite);
  }


}
