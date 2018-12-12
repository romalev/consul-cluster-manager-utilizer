package io.vertx.phone.dictionary.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Gateway service contract.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public interface PhoneDictionaryService {

    void savePhoneRecord(JsonObject phoneRecord, Handler<AsyncResult<Void>> resultHandler);

    void getPhoneRecord(String user, Handler<AsyncResult<JsonObject>> resultHandler);

    void getAllPhoneRecords(Handler<AsyncResult<List<JsonObject>>> resultHandler);
}
