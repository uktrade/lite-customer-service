package uk.gov.bis.lite.sar.model;

import java.util.List;

public class Users {

  private List<UserDetail> administrators;

  public Users(List<UserDetail> administrators) {
    this.administrators = administrators;
  }

  public List<UserDetail> getAdministrators() {
    return administrators;
  }
}
