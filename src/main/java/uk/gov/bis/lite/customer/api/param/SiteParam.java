package uk.gov.bis.lite.customer.api.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteParam {

  private String siteName;
  private AddressParam addressParam;

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public AddressParam getAddressParam() {
    return addressParam;
  }

  public void setAddressParam(AddressParam addressParam) {
    this.addressParam = addressParam;
  }
}
