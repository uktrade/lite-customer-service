package uk.gov.bis.lite.sar.spire;

import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.sar.spire.model.SpireCompany;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.List;

public class SpireCompanyClient extends SpireClient<List<SpireCompany>> {

  public SpireCompanyClient(SpireParser<List<SpireCompany>> parser) {
    super(parser);
  }
}
