package uk.gov.bis.lite.customer.spire.model;

import java.util.ArrayList;
import java.util.List;

public class SpireCompany {

  private String sarRef;
  private String companyName;
  private String shortName;
  private SpireOrganisationType spireOrganisationType;
  private String companyNumber;
  private String registeredAddress;
  private String registrationStatus;
  private String applicantType;
  private String natureOfBusiness;
  private String countryOfOrigin;
  private List<SpireWebsite> websites;

  public String getSarRef() {
    return sarRef;
  }

  public void setSarRef(String sarRef) {
    this.sarRef = sarRef;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public SpireOrganisationType getSpireOrganisationType() {
    return spireOrganisationType;
  }

  public void setSpireOrganisationType(SpireOrganisationType spireOrganisationType) {
    this.spireOrganisationType = spireOrganisationType;
  }

  public String getCompanyNumber() {
    return companyNumber;
  }

  public void setCompanyNumber(String companyNumber) {
    this.companyNumber = companyNumber;
  }

  public String getRegistrationStatus() {
    return registrationStatus;
  }

  public void setRegistrationStatus(String registrationStatus) {
    this.registrationStatus = registrationStatus;
  }

  public String getRegisteredAddress() {
    return registeredAddress;
  }

  public void setRegisteredAddress(String registeredAddress) {
    this.registeredAddress = registeredAddress;
  }

  public String getApplicantType() {
    return applicantType;
  }

  public void setApplicantType(String applicantType) {
    this.applicantType = applicantType;
  }

  public String getNatureOfBusiness() {
    return natureOfBusiness;
  }

  public void setNatureOfBusiness(String natureOfBusiness) {
    this.natureOfBusiness = natureOfBusiness;
  }

  public String getCountryOfOrigin() {
    return countryOfOrigin;
  }

  public void setCountryOfOrigin(String countryOfOrigin) {
    this.countryOfOrigin = countryOfOrigin;
  }

  public List<SpireWebsite> getWebsites() {
    return websites != null ? websites : new ArrayList<>();
  }

  public void setWebsites(List<SpireWebsite> websites) {
    this.websites = websites;
  }
}
