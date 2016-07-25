package uk.gov.bis.lite.sar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.stream.Collectors;

@JsonPropertyOrder({"sarRef", "companyName", "companyNumber", "shortName", "organisationType",
    "registrationStatus", "applicantType", "countryOfOriginCode"})
public class CompanyWrapper {

  private Company company;

  public CompanyWrapper(Company company) {
    this.company = company;
  }

  @JsonProperty("sarRef")
  public String sarRef() {
    return company.getSarRef();
  }

  @JsonProperty("companyName")
  public String companyName() {
    return company.getName();
  }

  @JsonProperty("companyNumber")
  public String companyNumber() {
    return company.getNumber();
  }

  @JsonProperty("shortName")
  public String shortName() {
    return company.getShortName();
  }

  @JsonProperty("organisationType")
  public String organisationType() {
    return company.getOrganisationType().getTypeLongName();
  }

  @JsonProperty("registrationStatus")
  public String registrationStatus() {
    return company.getRegistrationStatus();
  }

  @JsonProperty("applicantType")
  public String applicantType() {
    return company.getApplicantType();
  }

  @JsonProperty("countryOfOriginCode")
  public String countryOfOriginCode() {
    return company.getCountryOfOrigin();
  }

  @JsonProperty("websites")
  public List<String> websites() {
    return company.getWebsites().stream().map(Website::getUrl).collect(Collectors.toList());
  }
}
