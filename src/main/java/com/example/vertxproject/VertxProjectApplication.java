package com.example.vertxproject;

import io.vertx.core.Handler;
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

        setTimers(vertx);
;    }

    public static void setTimers(Vertx vertx) throws InterruptedException{
        long timerOneID = vertx.setTimer(2500, new Handler<Long>() {
            @Override
            public void handle(Long time) {
                System.out.println("One timer fired " + time);
            }
        });

        long timerPeriodID = vertx.setPeriodic(2000, new Handler<Long>() {
            @Override
            public void handle(Long time) {
                System.out.println("Period timer fired: " + time);
            }
        });

        Thread.sleep(10000);
        vertx.cancelTimer(timerPeriodID);
    }
}
