package uk.gov.bis.lite.sar.config.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.setup.Environment;
import uk.gov.bis.lite.sar.config.CustomerApplicationConfiguration;
import uk.gov.bis.lite.spire.SpireClient;
import uk.gov.bis.lite.spire.SpireUnmarshaller;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  SpireClient provideSpireClient(Environment environment, CustomerApplicationConfiguration config) {
    SpireClient client = new SpireClient();
    client.init(config.getSpireClientUserName(), config.getSpireClientPassword(), config.getSpireClientUrl());
    return client;
  }

  @Provides
  @Singleton
  SpireUnmarshaller provideSpireUnmarshaller(Environment environment, CustomerApplicationConfiguration config) {
    return new SpireUnmarshaller();
  }

  @Override
  protected void configure() {
  }

}
