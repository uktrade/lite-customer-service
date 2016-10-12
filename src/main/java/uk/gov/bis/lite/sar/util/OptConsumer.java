package uk.gov.bis.lite.sar.util;

import java.util.Optional;
import java.util.function.Consumer;

public class OptConsumer<T> {
  private Optional<T> optional;

  private OptConsumer(Optional<T> optional) {
    this.optional = optional;
  }

  public static <T> OptConsumer<T> of(Optional<T> optional) {
    return new OptConsumer<>(optional);
  }

  public OptConsumer<T> ifPresent(Consumer<T> c) {
    optional.ifPresent(c);
    return this;
  }

  public OptConsumer<T> ifNotPresent(Runnable r) {
    if (!optional.isPresent()) {
      r.run();
    }
    return this;
  }
}
