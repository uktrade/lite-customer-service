package uk.gov.bis.lite.sar.spire;


import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

public class SpireReferenceClient extends SpireClient<String> {

  public SpireReferenceClient(SpireParser<String> parser) {
    super(parser);
  }
}
