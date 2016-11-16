package uk.gov.bis.lite.customer.spire;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.gov.bis.lite.customer.spire.model.SpireSite;
import uk.gov.bis.lite.customer.spire.parsers.SiteParser;

import java.util.List;

public class ParseSitesTest extends SpireParseTest {

  @Test
  public void testSitesParser() {
    List<SpireSite> sites = new SiteParser().parseResponse(createSpireResponse("spire/sites.xml"));

    assertThat(sites).hasSize(3);
    assertThat(sites).extracting("sarRef").contains("SAR1", "SAR2", "SAR2");
    assertThat(sites).filteredOn(comp -> comp.getOccupancyStatus().equals("ACTIVE")).hasSize(2);
  }

}
