package com.example.vertxproject;

import com.example.vertxproject.verticles.HttpServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(VertxUnitRunner.class)
@SpringBootTest
public class VertxProjectApplicationTests {

    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(HttpServerVerticle.class.getName(),
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(8081, "localhost", "/",
                response ->
                        response.handler(body -> {
                            context.assertTrue(body.toString().contains("Hello"));
                            async.complete();
                        }));
    }

}
