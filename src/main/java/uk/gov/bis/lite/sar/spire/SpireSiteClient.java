package uk.gov.bis.lite.sar.spire;

import uk.gov.bis.lite.sar.spire.model.SpireSite;
import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.List;

public class SpireSiteClient extends SpireClient<List<SpireSite>> {

  public SpireSiteClient(SpireParser<List<SpireSite>> parser) {
    super(parser);
  }
}
