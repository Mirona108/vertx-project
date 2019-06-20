package com.example.vertxproject.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class EventBusSenderVerticle extends AbstractVerticle {
    //Sending messages
    public void start(Future<Void> future) {
        //to all verticle
        vertx.eventBus().publish("anAddress", "message 2");
        //to one verticle
        vertx.eventBus().send("anAddress", "message 1");
    }
}
