package uk.gov.bis.lite.sar.mocks;

import uk.gov.bis.lite.common.item.in.SiteIn;
import uk.gov.bis.lite.common.item.out.SiteOut;
import uk.gov.bis.lite.sar.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteServiceMock implements SiteService {

  private SiteOut mockSite = null;
  private List<SiteOut> mockSites = new ArrayList<>();
  private String mockSiteId = "id1";

  public SiteServiceMock(String mockSiteId, int numberOfSites) {
    this.mockSiteId = mockSiteId;
    initSites(numberOfSites);
  }

  private void initSites(int numberOfSites) {

    mockSite = new SiteOut();
    mockSite.setSiteId(mockSiteId);
    for (int i = 0; i < numberOfSites; i++) {
      SiteOut site = new SiteOut();
      site.setCustomerId("site" + i);
      mockSites.add(site);
    }
  }

  public String createSite(SiteIn siteItemIn, String customerId, String userId) {
    return mockSiteId;
  }


  public List<SiteOut> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteOut> getSite(String siteId) {
    return Optional.of(mockSite);
  }


}
