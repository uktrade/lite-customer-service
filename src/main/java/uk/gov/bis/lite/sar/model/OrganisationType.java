package uk.gov.bis.lite.sar.model;

public enum OrganisationType {
  TC("Trading Company");

  private String typeLongName;

  OrganisationType(String typeLongName) {
    this.typeLongName = typeLongName;
  }

  public String getTypeLongName() {
    return typeLongName;
  }
}
