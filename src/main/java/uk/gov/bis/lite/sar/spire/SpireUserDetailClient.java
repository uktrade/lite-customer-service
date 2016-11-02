package uk.gov.bis.lite.sar.spire;

import uk.gov.bis.lite.sar.spire.model.SpireUserDetail;
import uk.gov.bis.lite.spire.client.SpireClient;
import uk.gov.bis.lite.spire.client.SpireClientConfig;
import uk.gov.bis.lite.spire.client.SpireRequestConfig;
import uk.gov.bis.lite.spire.client.parser.SpireParser;

import java.util.List;

public class SpireUserDetailClient extends SpireClient<List<SpireUserDetail>> {

  public SpireUserDetailClient(SpireParser<List<SpireUserDetail>> parser,
                               SpireClientConfig clientConfig,
                               SpireRequestConfig requestConfig) {
    super(parser, clientConfig, requestConfig);
  }
}
