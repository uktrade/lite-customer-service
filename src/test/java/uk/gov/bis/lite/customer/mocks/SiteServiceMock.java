package uk.gov.bis.lite.customer.mocks;

import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SiteServiceMock implements SiteService {

  private static final String SITE_ID = "EXISTING_SITE";
  private List<SiteView> mockSites = new ArrayList<>();
  private SiteView mockSiteView;

  public SiteServiceMock() {
    this(SITE_ID, 1);
  }

  public SiteServiceMock(String mockSiteId, int numberOfSites) {
    initSites(mockSiteId, numberOfSites);
  }

  private void initSites(String mockSiteId, int numberOfSites) {

    mockSiteView = new SiteView();
    mockSiteView.setCustomerId("CUST1");
    mockSiteView.setSiteId(mockSiteId);
    mockSiteView.setSiteName("SITE");
    SiteView.SiteViewAddress address = new SiteView.SiteViewAddress();
    address.setCountry("CTRY0");
    address.setPlainText("UK");
    mockSiteView.setAddress(address);

    for (int i = 0; i < numberOfSites; i++) {
      SiteView site = new SiteView();
      site.setCustomerId("CUST" + i);
      site.setSiteName("SITE");
      site.setSiteId(mockSiteId + i);
      site.setAddress(address);
      mockSites.add(site);
    }
  }

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {
    return Optional.of(mockSiteView);
  }

  public List<SiteView> getSites(String customerId, String userId) {
    return mockSites;
  }

  public Optional<SiteView> getSite(String siteId) {
    if (SITE_ID.equals(siteId)) {
      return Optional.of(mockSiteView);
    }
    return Optional.empty();
  }


}
