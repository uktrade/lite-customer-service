package uk.gov.bis.lite.customer.spire;

import uk.gov.bis.lite.common.spire.client.SpireClient;
import uk.gov.bis.lite.common.spire.client.SpireClientConfig;
import uk.gov.bis.lite.common.spire.client.SpireRequestConfig;
import uk.gov.bis.lite.common.spire.client.parser.SpireParser;
import uk.gov.bis.lite.customer.spire.model.SpireCompany;

import java.util.List;

public class SpireCompanyClient extends SpireClient<List<SpireCompany>> {

  public SpireCompanyClient(SpireParser<List<SpireCompany>> parser,
                            SpireClientConfig clientConfig,
                            SpireRequestConfig requestConfig) {
    super(parser, clientConfig, requestConfig);
  }
}
