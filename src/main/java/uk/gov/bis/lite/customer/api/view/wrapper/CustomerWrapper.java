package uk.gov.bis.lite.customer.api.view.wrapper;

import uk.gov.bis.lite.customer.api.view.CustomerView;

public class CustomerWrapper {

  private CustomerView customer;

  public CustomerWrapper(CustomerView customer) {
    this.customer = customer;
  }

  public CustomerView unwrap() {
    return customer;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof CustomerWrapper) {
      return ((CustomerWrapper) obj).customer.getCustomerId().equals(customer.getCustomerId());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return customer.getCompanyNumber().hashCode();
  }

}
