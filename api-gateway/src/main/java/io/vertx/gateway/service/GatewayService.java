package io.vertx.gateway.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Gateway service contract.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public interface GatewayService {

  void savePhoneRecord(JsonObject phoneRecord, Handler<AsyncResult<Void>> resultHandler);

  void getPhoneRecord(String user, Handler<AsyncResult<JsonObject>> resultHandler);

  void getAllPhoneRecords(Handler<AsyncResult<JsonObject>> resultHandler);
}
