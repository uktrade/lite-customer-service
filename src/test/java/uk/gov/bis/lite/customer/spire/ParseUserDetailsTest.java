package uk.gov.bis.lite.customer.spire;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import uk.gov.bis.lite.customer.spire.model.SpireUserDetail;
import uk.gov.bis.lite.customer.spire.parsers.UserDetailParser;

import java.util.List;

public class ParseUserDetailsTest extends SpireParseTest {

  @Test
  public void testUserDetailsParser() {

    List<SpireUserDetail> userDetails = new UserDetailParser().parseResponse(createSpireResponse("spire/userDetails.xml"));

    assertThat(userDetails).hasSize(4);
    assertThat(userDetails).extracting("roleName")
        .contains("SAR_ADMINISTRATOR", "APPLICATION_PREPARER", "APPLICATION_SUBMITTER", "LICENCE_RETURN_CONTACT");
    assertThat(userDetails).filteredOn(u -> u.getSurname().equals("surname4")).hasSize(1);
  }

}
