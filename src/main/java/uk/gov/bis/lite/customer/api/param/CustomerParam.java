package uk.gov.bis.lite.customer.api.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerParam {

  private String userId;
  private String customerName;
  private String customerType;
  private AddressParam addressParam;
  private String website;
  private String companiesHouseNumber;
  private Boolean companiesHouseValidated;
  private String eoriNumber;
  private Boolean eoriValidated;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public AddressParam getAddressParam() {
    return addressParam;
  }

  public void setAddressParam(AddressParam addressParam) {
    this.addressParam = addressParam;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getCompaniesHouseNumber() {
    return companiesHouseNumber;
  }

  public void setCompaniesHouseNumber(String companiesHouseNumber) {
    this.companiesHouseNumber = companiesHouseNumber;
  }

  public Boolean getCompaniesHouseValidated() {
    return companiesHouseValidated;
  }

  public void setCompaniesHouseValidated(Boolean companiesHouseValidated) {
    this.companiesHouseValidated = companiesHouseValidated;
  }

  public String getEoriNumber() {
    return eoriNumber;
  }

  public void setEoriNumber(String eoriNumber) {
    this.eoriNumber = eoriNumber;
  }

  public Boolean getEoriValidated() {
    return eoriValidated;
  }

  public void setEoriValidated(Boolean eoriValidated) {
    this.eoriValidated = eoriValidated;
  }
}
