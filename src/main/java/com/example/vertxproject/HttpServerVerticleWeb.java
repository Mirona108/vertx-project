package com.example.vertxproject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class HttpServerVerticleWeb extends AbstractVerticle {
    private HttpServer httpServer = null;

    @Override
    public void start(Future future) {
        Router router = Router.router(vertx);
        // Bind "/" to our hello message - so we are still compatible with out tests.
        router.route("/").handler(rc -> {
            rc.response().putHeader("content-type", "text/html")
                         .end("<pre><h1>Hello from my first Vert.x 3 app</h1> </pre> ");
        });

        httpServer = vertx.createHttpServer();

        httpServer.requestHandler(router::accept);
        httpServer.listen(8081, result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }
}
