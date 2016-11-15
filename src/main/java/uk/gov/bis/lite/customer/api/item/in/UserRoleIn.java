package uk.gov.bis.lite.customer.api.item.in;

public class UserRoleIn {

  private String adminUserId;
  private RoleType roleType;

  public enum RoleType {
    ADMIN, SUBMITTER, PREPARER;
  }

  public String getAdminUserId() {
    return adminUserId;
  }

  public void setAdminUserId(String adminUserId) {
    this.adminUserId = adminUserId;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }
}
