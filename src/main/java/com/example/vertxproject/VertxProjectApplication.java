package com.example.vertxproject;

import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VertxProjectApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(VertxProjectApplication.class, args);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BasicVerticle());

        vertx.deployVerticle(new EventBusReceiverVerticle("R1"));
        vertx.deployVerticle(new EventBusReceiverVerticle("R2"));

        Thread.sleep(3000);
        vertx.deployVerticle(new EventBusSenderVerticle());
    }
}
