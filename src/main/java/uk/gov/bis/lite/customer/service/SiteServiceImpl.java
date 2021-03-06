package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.spire.SpireReferenceClient;
import uk.gov.bis.lite.customer.spire.SpireSiteClient;
import uk.gov.bis.lite.customer.spire.model.SpireSite;
import uk.gov.bis.lite.customer.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class SiteServiceImpl implements SiteService {

  private final SpireReferenceClient createSiteForSarReferenceClient;
  private final SpireSiteClient siteClient;

  @Inject
  public SiteServiceImpl(@Named("SpireCreateSiteForSarClient") SpireReferenceClient createSiteForSarReferenceClient,
                         SpireSiteClient siteClient) {
    this.createSiteForSarReferenceClient = createSiteForSarReferenceClient;
    this.siteClient = siteClient;
  }

  public Optional<SiteView> createSite(SiteParam param, String customerId, String userId) {

    if (!StringUtils.isBlank(userId) && param.getAddressParam() != null) {
      SpireRequest request = createSiteForSarReferenceClient.createRequest();
      request.addChild("VERSION_NO", "1.0");
      request.addChild("WUA_ID", userId);
      request.addChild("SAR_REF", customerId);
      request.addChild("DIVISION", param.getSiteName());
      request.addChild("LITE_ADDRESS", Util.getAddressParamJson(param.getAddressParam()));
      request.addChild("ADDRESS", Util.getFriendlyAddress(param.getAddressParam()));
      request.addChild("COUNTRY_REF", param.getAddressParam().getCountry());

      return getSite(createSiteForSarReferenceClient.sendRequest(request));
    } else {
      throw new SpireClientException("Mandatory fields missing: userId and/or address");
    }
  }

  public List<SiteView> getSitesByUserId(String customerId, String userId) {
    SpireRequest request = siteClient.createRequest();
    request.addChild("userId", userId);
    request.addChild("sarRef", customerId);
    List<SpireSite> spireSites = siteClient.sendRequest(request);
    return spireSites.stream().map(this::getSiteOut).collect(Collectors.toList());
  }

  public List<SiteView> getSitesByCustomerId(String customerId) {
    SpireRequest request = siteClient.createRequest();
    request.addChild("sarRef", customerId);
    List<SpireSite> spireSites = siteClient.sendRequest(request);
    return spireSites.stream().map(this::getSiteOut).collect(Collectors.toList());
  }

  public Optional<SiteView> getSite(String siteId) {
    SpireRequest request = siteClient.createRequest();
    request.addChild("siteRef", siteId);
    List<SpireSite> spireSites = siteClient.sendRequest(request);
    if (!spireSites.isEmpty()) {
      return Optional.of(getSiteOut(spireSites.get(0)));
    }
    return Optional.empty();
  }

  private SiteView getSiteOut(SpireSite spireSite) {
    SiteView siteView = new SiteView();
    siteView.setCustomerId(spireSite.getSarRef());
    siteView.setSiteId(spireSite.getSiteRef());
    String companyName = spireSite.getCompanyName() != null ? spireSite.getCompanyName() : "";
    String siteName = spireSite.getDivision() != null ? spireSite.getDivision() : companyName;
    siteView.setSiteName(siteName);

    SiteView.SiteViewAddress address = new SiteView.SiteViewAddress();
    address.setPlainText(spireSite.getAddress());
    address.setCountry(spireSite.getCountryRef());
    siteView.setAddress(address);
    return siteView;
  }

}
