package uk.gov.bis.lite.customer.api.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteView {

  private String customerId;
  private String siteId;
  private String siteName;
  private SiteViewAddress address;

  public static class SiteViewAddress {

    private String plainText;
    private String country;

    public String getPlainText() {
      return plainText;
    }

    public void setPlainText(String plainText) {
      this.plainText = plainText;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public SiteViewAddress getAddress() {
    return address;
  }

  public void setAddress(SiteViewAddress address) {
    this.address = address;
  }
}
