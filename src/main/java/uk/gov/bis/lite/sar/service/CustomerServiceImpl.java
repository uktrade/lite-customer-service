package uk.gov.bis.lite.sar.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.bis.lite.common.item.in.CustomerIn;
import uk.gov.bis.lite.common.item.out.CustomerOut;
import uk.gov.bis.lite.common.spire.client.SpireRequest;
import uk.gov.bis.lite.common.spire.client.exception.SpireClientException;
import uk.gov.bis.lite.sar.spire.SpireCompanyClient;
import uk.gov.bis.lite.sar.spire.SpireReferenceClient;
import uk.gov.bis.lite.sar.spire.model.SpireCompany;
import uk.gov.bis.lite.sar.spire.model.SpireWebsite;
import uk.gov.bis.lite.sar.util.Util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class CustomerServiceImpl implements CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

  private SpireReferenceClient createLiteSarReferenceClient;
  private SpireCompanyClient companyClient;

  @Inject
  public CustomerServiceImpl(@Named("SpireCreateLiteSarClient") SpireReferenceClient createLiteSarReferenceClient,
                             SpireCompanyClient companyClient) {
    this.createLiteSarReferenceClient = createLiteSarReferenceClient;
    this.companyClient = companyClient;
  }

  public String createCustomer(CustomerIn item) {

    if (!StringUtils.isBlank(item.getUserId()) && item.getAddressItem() != null) {
      SpireRequest request = createLiteSarReferenceClient.createRequest();
      request.addChild("VERSION_NO", "1.1");
      request.addChild("WUA_ID", item.getUserId());
      request.addChild("CUSTOMER_NAME", item.getCustomerName());
      request.addChild("CUSTOMER_TYPE", item.getCustomerType());
      request.addChild("LITE_ADDRESS", Util.getAddressItemJson(item.getAddressItem()));
      request.addChild("ADDRESS", Util.getFriendlyAddress(item.getAddressItem()));
      request.addChild("COUNTRY_REF", item.getAddressItem().getCountry());
      request.addChild("WEBSITE", item.getWebsite());
      String companiesHouseNumber = item.getCompaniesHouseNumber();
      if (!StringUtils.isBlank(companiesHouseNumber)) {
        request.addChild("COMPANIES_HOUSE_NUMBER", companiesHouseNumber);
        request.addChild("COMPANIES_HOUSE_VALIDATED", item.getCompaniesHouseValidatedStr());
      }
      String eoriNumber = item.getEoriNumber();
      if (!StringUtils.isBlank(eoriNumber)) {
        request.addChild("EORI_NUMBER", eoriNumber);
        request.addChild("EORI_VALIDATED", item.getEoriValidatedStr());
      }
      return createLiteSarReferenceClient.sendRequest(request);
    } else {
      throw new SpireClientException("Mandatory fields missing: userId and/or address");
    }
  }


  public List<CustomerOut> getCustomersBySearch(String postcode) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("postCode", postcode);
    return companyClient.sendRequest(request).stream().map(customerOutFunction).collect(Collectors.toList());
  }

  public List<CustomerOut> getCustomersBySearch(String postcode, String eoriNumber) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("postCode", postcode);
    request.addChild("eoriNumber", eoriNumber);
    return companyClient.sendRequest(request).stream().map(customerOutFunction).collect(Collectors.toList());
  }

  public List<CustomerOut> getCustomersByUserId(String userId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("userId", userId);
    return companyClient.sendRequest(request).stream().map(customerOutFunction).collect(Collectors.toList());
  }

  public Optional<CustomerOut> getCustomerById(String customerId) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("sarRef", customerId);
    List<SpireCompany> spireCompanies = companyClient.sendRequest(request);
    if (spireCompanies.size() > 0) {
      LOGGER.info("Found " + spireCompanies.size() + " spire companies for sarRef: " + customerId);
      return Optional.of(customerOutFunction.apply(spireCompanies.get(0)));
    }
    return Optional.empty();
  }

  public List<CustomerOut> getCustomersByCompanyNumber(String companyNumber) {
    SpireRequest request = companyClient.createRequest();
    request.addChild("companyNumber", companyNumber);
    return companyClient.sendRequest(request).stream().map(customerOutFunction).collect(Collectors.toList());
  }

  /**
   * Maps SpireCompany to Customer

   private Function<SpireCompany, Customer> customerFunction = Customer::new;*/

  /**
   * Maps SpireCompany to CustomerOut
   */
  private Function<SpireCompany, CustomerOut> customerOutFunction = spireCompany -> {
    CustomerOut out = new CustomerOut();
    out.setCompanyName(spireCompany.getName());
    out.setApplicantType(spireCompany.getApplicantType());
    out.setCompanyNumber(spireCompany.getNumber());
    out.setCountryOfOriginCode(spireCompany.getCountryOfOrigin());
    out.setOrganisationType(spireCompany.getSpireOrganisationType() != null ? spireCompany.getSpireOrganisationType().getTypeLongName() : null);
    out.setRegisteredAddress(spireCompany.getRegisteredAddress());
    out.setRegistrationStatus(spireCompany.getRegistrationStatus());
    out.setSarRef(spireCompany.getSarRef());
    out.setShortName(spireCompany.getShortName());
    out.setWebsites(spireCompany.getWebsites().stream().map(SpireWebsite::getUrl).collect(Collectors.toList()));
    return out;
  };
}
