package uk.gov.bis.lite.customer.spire;

import uk.gov.bis.lite.common.spire.client.SpireClient;
import uk.gov.bis.lite.common.spire.client.SpireClientConfig;
import uk.gov.bis.lite.common.spire.client.SpireRequestConfig;
import uk.gov.bis.lite.common.spire.client.parser.SpireParser;
import uk.gov.bis.lite.customer.spire.model.SpireUserDetail;

import java.util.List;

public class SpireUserDetailClient extends SpireClient<List<SpireUserDetail>> {

  public SpireUserDetailClient(SpireParser<List<SpireUserDetail>> parser,
                               SpireClientConfig clientConfig,
                               SpireRequestConfig requestConfig) {
    super(parser, clientConfig, requestConfig);
  }
}
