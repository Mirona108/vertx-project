package com.example.vertxproject.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class EventBusReceiverVerticle extends AbstractVerticle {

    String name;

    public EventBusReceiverVerticle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void start(Future<Void> startFuture) {
        //Listening for messages
        vertx.eventBus().consumer("anAddress", message -> {
            System.out.println(this.name + " received message.body() = "
                    + message.body());
        });
    }
}
