package uk.gov.bis.lite.customer.spire;

import uk.gov.bis.lite.common.spire.client.SpireClient;
import uk.gov.bis.lite.common.spire.client.SpireClientConfig;
import uk.gov.bis.lite.common.spire.client.SpireRequestConfig;
import uk.gov.bis.lite.common.spire.client.parser.SpireParser;
import uk.gov.bis.lite.customer.spire.model.SpireSite;

import java.util.List;

public class SpireSiteClient extends SpireClient<List<SpireSite>> {

  public SpireSiteClient(SpireParser<List<SpireSite>> parser,
                         SpireClientConfig clientConfig,
                         SpireRequestConfig requestConfig) {
    super(parser, clientConfig, requestConfig);
  }
}
