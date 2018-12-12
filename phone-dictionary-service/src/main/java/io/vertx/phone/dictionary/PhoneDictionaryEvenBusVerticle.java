package io.vertx.phone.dictionary;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.phone.dictionary.service.PhoneDictionaryService;

/**
 * Encapsulates simple event bus consumer.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class PhoneDictionaryEvenBusVerticle extends AbstractVerticle {

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

        });

        vertx.eventBus().consumer(GET_PHONE_NUMBER_CHANNEL_NAME, event -> {

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
