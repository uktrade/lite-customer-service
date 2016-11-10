package uk.gov.bis.lite.sar.spire;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.gov.bis.lite.sar.spire.model.SpireCompany;
import uk.gov.bis.lite.sar.spire.parsers.CompanyParser;

import java.util.List;

public class ParseCompaniesTest extends SpireParseTest {

  @Test
  public void testCompanyParser() {
    List<SpireCompany> companies = new CompanyParser().parseResponse(createSpireResponse("spire/companies.xml"));

    assertThat(companies).hasSize(3);
    assertThat(companies).extracting("sarRef").contains("SAR1", "SAR2", "SAR2");
    assertThat(companies).filteredOn(comp -> comp.getWebsites().size() > 0).hasSize(1);
  }

}
