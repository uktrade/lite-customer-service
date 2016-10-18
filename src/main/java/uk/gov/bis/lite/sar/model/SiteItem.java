package uk.gov.bis.lite.sar.model;

import org.apache.commons.lang3.StringUtils;

public class SiteItem {

  private String userId;
  private String sarRef;
  private String siteName;
  private AddressItem addressItem;

  public boolean hasMandatoryFields() {
    return !StringUtils.isBlank(userId) && addressItem != null;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSarRef() {
    return sarRef;
  }

  public void setSarRef(String sarRef) {
    this.sarRef = sarRef;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public AddressItem getAddressItem() {
    return addressItem;
  }

  public void setAddressItem(AddressItem addressItem) {
    this.addressItem = addressItem;
  }
}
