package io.vertx.phone.dictionary;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.phone.dictionary.service.PhoneDictionaryService;

/**
 * Encapsulates simple event bus consumer.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class PhoneDictionaryEvenBusVerticle extends AbstractVerticle {

  private final static Logger log = LoggerFactory.getLogger(PhoneDictionaryEvenBusVerticle.class);

  private final static String SAVE_PHONE_RECORD_CHANNEL_NAME = "savePhoneRecord";
  private final static String GET_PHONE_NUMBER_CHANNEL_NAME = "getPhoneRecord";
  private final static String GET_ALL_PHONE_RECORDS_CHANNEL_NAME = "getAllPhoneRecords";

  private final PhoneDictionaryService phoneDictionaryService;

  PhoneDictionaryEvenBusVerticle(PhoneDictionaryService phoneDictionaryService) {
    this.phoneDictionaryService = phoneDictionaryService;
  }

  @Override
  public void start(Future<Void> startFuture) {
    vertx.eventBus().consumer(SAVE_PHONE_RECORD_CHANNEL_NAME, event -> {
      if (event.body() instanceof JsonObject) {
        JsonObject jsonObject = (JsonObject) event.body();
        phoneDictionaryService.savePhoneRecord(jsonObject, resultHandler -> {
        });
      } else {
        log.trace("{} not a type of JsonObject.", event.body().toString());
      }
    });

    vertx.eventBus().consumer(GET_PHONE_NUMBER_CHANNEL_NAME, event -> {
      if (event.body() instanceof String) {
        String user = (String) event.body();

      } else {
        log.trace("{} not a type of String.", event.body().toString());
      }
    });

    vertx.eventBus().consumer(GET_ALL_PHONE_RECORDS_CHANNEL_NAME, event -> {

    });

    startFuture.complete();
  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }
}
