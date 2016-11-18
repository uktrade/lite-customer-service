package uk.gov.bis.lite.customer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.customer.api.param.SiteParam;
import uk.gov.bis.lite.customer.api.view.SiteView;
import uk.gov.bis.lite.customer.exception.SpireForbiddenException;
import uk.gov.bis.lite.customer.spire.SpireReferenceClient;
import uk.gov.bis.lite.customer.spire.SpireSiteClient;
import uk.gov.bis.lite.customer.spire.model.SpireSite;
import uk.gov.bis.lite.customer.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class SiteServiceImpl implements SiteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

  private SpireReferenceClient createSiteForSarReferenceClient;
  private SpireSiteClient siteClient;

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

      Optional<SiteView> siteView = Optional.empty();
      try {
        siteView = getSite(createSiteForSarReferenceClient.sendRequest(request));
      } catch (SpireClientException e) {
        throwErrorMessageMappedException(e);
      }
      return siteView;
    } else {
      throw new SpireClientException("Mandatory fields missing: userId and/or address");
    }
  }

  /**
   * Throw different type of exception if error message mapping found, rethrow original otherwise
   */
  private void throwErrorMessageMappedException(SpireClientException exception) {
    String errorMessage = exception.getMessage();
    if (errorMessage != null && errorMessage.contains("USER_LACKS_PRIVILEGES")) {
      throw new SpireForbiddenException("USER_LACKS_PRIVILEGES");
    } else {
      throw exception;
    }
  }

  public List<SiteView> getSites(String customerId, String userId) {
    SpireRequest request = siteClient.createRequest();
    request.addChild("userId", userId);
    request.addChild("sarRef", customerId);
    List<SpireSite> spireSites = siteClient.sendRequest(request);
    return spireSites.stream().map(this::getSiteOut).collect(Collectors.toList());
  }

  public Optional<SiteView> getSite(String siteId) {
    SpireRequest request = siteClient.createRequest();
    request.addChild("siteRef", siteId);
    List<SpireSite> spireSites = siteClient.sendRequest(request);
    if (spireSites.size() > 0) {
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
