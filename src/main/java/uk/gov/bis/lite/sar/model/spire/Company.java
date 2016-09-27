package uk.gov.bis.lite.sar.model.spire;

import java.util.ArrayList;
import java.util.List;

public class Company {

  private String sarRef;
  private String name;
  private String shortName;
  private OrganisationType organisationType;
  private String number;
  private String registeredAddress;
  private String registrationStatus;
  private String applicantType;
  private String natureOfBusiness;
  private String countryOfOrigin;
  private List<Website> websites;


  public String getSarRef() {
    return sarRef;
  }

  public void setSarRef(String sarRef) {
    this.sarRef = sarRef;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public OrganisationType getOrganisationType() {
    return organisationType;
  }

  public void setOrganisationType(OrganisationType organisationType) {
    this.organisationType = organisationType;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
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

  public List<Website> getWebsites() {
    return websites != null ? websites : new ArrayList<>();
  }

  public void setWebsites(List<Website> websites) {
    this.websites = websites;
  }
}
