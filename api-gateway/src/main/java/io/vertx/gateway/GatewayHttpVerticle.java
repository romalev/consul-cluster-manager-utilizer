package io.vertx.gateway;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.gateway.service.GatewayService;

import java.util.Objects;

/**
 * Encapsulates simple http server.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public final class GatewayHttpVerticle extends AbstractVerticle {

  private final static Logger log = LoggerFactory.getLogger(GatewayHttpVerticle.class);

  private final static String ROOT_URL = "/gateway";
  private final static String HEALTH_CHECK_URL = ROOT_URL + "/checkHealth";
  private final static String STORE_PHONE_RECORD_URL = ROOT_URL + "/storePhoneRecord";
  private final static String GET_PHONE_RECORD_URL = ROOT_URL + "/getPhoneRecord";
  private final static String GET_ALL_PHONE_RECORDS_URL = ROOT_URL + "/getAllPhoneRecords";

  private final GatewayService gatewayService;

  GatewayHttpVerticle(GatewayService gatewayService) {
    this.gatewayService = gatewayService;
  }

  @Override
  public void start(Future<Void> startFuture) {
    log.trace("Staring {}...", GatewayHttpVerticle.class.getSimpleName());
    final HttpServer httpServer = vertx.createHttpServer();
    final Router router = Router.router(vertx);

    router.route(ROOT_URL + "*").handler(BodyHandler.create());
    router.get(HEALTH_CHECK_URL).handler(event ->
      event.response().setStatusCode(HttpResponseStatus.OK.code()).end(GatewayHttpVerticle.class.getName() + " is healthy!"));

    router.get(GET_ALL_PHONE_RECORDS_URL).handler(event -> {
      gatewayService.getAllPhoneRecords(resultHandler -> {
        if (resultHandler.succeeded()) {
          log.trace("{} -> {}", GET_ALL_PHONE_RECORDS_URL, resultHandler.result());
          event.response()
            .setStatusCode(HttpResponseStatus.OK.code())
            .end(resultHandler.result().toString());
        } else {
          log.warn("{} -> Failed to execute.", GET_ALL_PHONE_RECORDS_URL, resultHandler.cause());
          event.response()
            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .end("Failed to execute your request: " + resultHandler.cause());
        }
      });
    });

    router.get(GET_PHONE_RECORD_URL).handler(event -> {
      String user = event.request().getParam("user");
      if (Objects.isNull(user) || user.isEmpty()) {
        log.warn("{} -> user http parameter is not specified.", GET_PHONE_RECORD_URL);
        event.response()
          .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
          .end("user http parameter must be specified");
      } else {
        gatewayService.getPhoneRecord(user, resultHandler -> {
          if (resultHandler.succeeded()) {
            event.response()
              .setStatusCode(HttpResponseStatus.OK.code())
              .end(resultHandler.result().toString());
          } else {
            log.warn("{} -> Failed to execute.", GET_PHONE_RECORD_URL, resultHandler.cause());
            event.response()
              .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
              .end("Failed to execute your request: " + resultHandler.cause());
          }
        });
      }
    });

    router.post(STORE_PHONE_RECORD_URL).handler(event -> {
      // no validation
      gatewayService.savePhoneRecord(event.getBodyAsJson(), resultHandler -> {
        if (resultHandler.succeeded()) {
          event.response()
            .setStatusCode(HttpResponseStatus.OK.code())
            .end(String.valueOf(resultHandler.succeeded()));
        } else {
          log.warn("{} -> Failed to execute.", STORE_PHONE_RECORD_URL, resultHandler.cause());
          event.response()
            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .end("Failed to execute your request: " + resultHandler.cause());
        }
      });
    });

    httpServer.requestHandler(router).listen(7878, event -> {
      if (event.succeeded()) {
        log.debug("Verticle: {} has been started on: {}", GatewayHttpVerticle.class.getSimpleName(), event.result().actualPort());
        startFuture.complete();
      } else {
        log.warn("Verticle: {} failed to start.", GatewayHttpVerticle.class.getSimpleName(), event.cause());
        startFuture.fail(event.cause());
      }
    });

  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }
}
