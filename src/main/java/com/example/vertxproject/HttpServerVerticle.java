package com.example.vertxproject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;

public class HttpServerVerticle extends AbstractVerticle {
    private HttpServer httpServer = null;

    @Override
    public void start(Future future) {
        httpServer = vertx.createHttpServer();

        httpServer.requestHandler(r -> r.response().end(" <h1>Hello from my first Vert.x application</h1> "));
        httpServer.listen(8081, result -> {
                                    if (result.succeeded()) {
                                        future.complete();
                                    } else {
                                        future.fail(result.cause());
                                    }
                        });
    }
}
