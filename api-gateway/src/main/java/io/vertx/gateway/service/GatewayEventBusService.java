package io.vertx.gateway.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Gateway event bus based service implementation.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class GatewayEventBusService implements GatewayService {

  private final static String SAVE_PHONE_RECORD_CHANNEL_NAME = "savePhoneRecord";
  private final static String GET_PHONE_NUMBER_CHANNEL_NAME = "getPhoneRecord";
  private final static String GET_ALL_PHONE_RECORDS_CHANNEL_NAME = "getAllPhoneRecords";
  private final Vertx vertx;

  public GatewayEventBusService(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void savePhoneRecord(JsonObject phoneRecord, Handler<AsyncResult<Void>> resultHandler) {
    vertx.eventBus().publish(SAVE_PHONE_RECORD_CHANNEL_NAME, phoneRecord);
    resultHandler.handle(Future.succeededFuture());
  }

  @Override
  public void getPhoneRecord(String user, Handler<AsyncResult<JsonObject>> resultHandler) {
    vertx.eventBus().send(GET_PHONE_NUMBER_CHANNEL_NAME, user, event -> {
      if (event.succeeded()) {
        JsonObject reply = (JsonObject) event.result().body();
        resultHandler.handle(Future.succeededFuture(reply));
      } else {
        resultHandler.handle(Future.failedFuture(event.cause()));
      }
    });
  }

  @Override
  public void getAllPhoneRecords(Handler<AsyncResult<JsonObject>> resultHandler) {
    vertx.eventBus().send(GET_ALL_PHONE_RECORDS_CHANNEL_NAME, "", event -> {
      if (event.succeeded()) {
        final JsonObject result = (JsonObject) event.result().body();
        resultHandler.handle(Future.succeededFuture(result));
      } else {
        resultHandler.handle(Future.failedFuture(event.cause()));
      }
    });
  }
}
