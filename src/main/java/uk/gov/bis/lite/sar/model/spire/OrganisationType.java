package uk.gov.bis.lite.sar.model.spire;

public enum OrganisationType {
  TC("Trading Company"), O("Organisation");

  private String typeLongName;

  OrganisationType(String typeLongName) {
    this.typeLongName = typeLongName;
  }

  public String getTypeLongName() {
    return typeLongName;
  }
}
