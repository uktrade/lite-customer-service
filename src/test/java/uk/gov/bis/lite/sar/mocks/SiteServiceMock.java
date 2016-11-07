package uk.gov.bis.lite.sar.mocks;


import uk.gov.bis.lite.sar.model.Site;
import uk.gov.bis.lite.sar.model.item.SiteItem;
import uk.gov.bis.lite.sar.service.SiteService;
import uk.gov.bis.lite.sar.spire.model.SpireSite;

import java.util.ArrayList;
import java.util.List;

public class SiteServiceMock implements SiteService {

  private List<Site> mockSites = new ArrayList<>();
  private String mockSiteId = "id1";

  public SiteServiceMock(String mockSiteId, int numberOfSites) {
    this.mockSiteId = mockSiteId;
    initSites(numberOfSites);
  }

  private void initSites(int numberOfSites) {
    for (int i = 0; i < numberOfSites; i++) {
      SpireSite spireSite = new SpireSite();
      spireSite.setSiteRef("siteRef" + i);
      spireSite.setCompanyName("companyName" + i);
      mockSites.add(new Site(spireSite));
    }
  }

  public String createSite(SiteItem item) {
    return mockSiteId;
  }


  public List<Site> getSites(String customerId, String userId) {
    return mockSites;
  }


}
