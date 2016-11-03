package uk.gov.bis.lite.sar.spire.model;

public enum SpireOrganisationType {

  CC("County Council"),
  EGB("Environmental and Green Bodies"),
  GOV("Government Dept or Agency"),
  LA("Local Authority"),
  O("Other"),
  TC("Trading Company"),
  UA("Unitary Authority");

  private String typeLongName;

  SpireOrganisationType(String typeLongName) {
    this.typeLongName = typeLongName;
  }

  public String getTypeLongName() {
    return typeLongName;
  }
}
