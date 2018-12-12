package io.vertx.gateway;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.gateway.service.GatewayEventBusService;
import io.vertx.gateway.service.GatewayService;
import io.vertx.spi.cluster.consul.ConsulClusterManager;

/**
 * Boots the service up.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class GatewayLauncher {

    static {
        System.setProperty("vertx.logger-delegate-factory-class-name",
                "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }

    private static final Logger log = LoggerFactory.getLogger(GatewayLauncher.class);

    public static void main(String[] args) {
        boot();
    }

    private static void boot() {
        final ConsulClusterManager consulClusterManager = new ConsulClusterManager(getConsulClusterManagerConfig());
        final VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setHAEnabled(true);
        vertxOptions.setClusterManager(consulClusterManager);
        Vertx.clusteredVertx(vertxOptions, res -> {
            if (res.succeeded()) {
                log.debug("Clustered Vert.x instance has been created.");
                final Vertx vertx = res.result();
                final GatewayService gatewayService = new GatewayEventBusService(vertx);
                final GatewayHttpVerticle httpVerticle = new GatewayHttpVerticle(gatewayService);
                vertx.deployVerticle(httpVerticle, new DeploymentOptions().setHa(true));
            } else {
                log.error("Failed to boot: {}. Terminating the service.", GatewayLauncher.class.getSimpleName(), res.cause());
                shutDown();
            }
        });
    }

    private static void shutDown() {
        System.exit(0);
    }

    private static JsonObject getConsulClusterManagerConfig() {
        return new JsonObject()
                .put("host", "localhost")
                .put("port", 8500);
    }

}
