package com.example.vertxproject.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class BasicVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> future) {
        System.out.println("BasicVerticle started");
    }

    @Override
    public void stop() {
        System.out.println("BasicVerticle stopped");
    }
}
