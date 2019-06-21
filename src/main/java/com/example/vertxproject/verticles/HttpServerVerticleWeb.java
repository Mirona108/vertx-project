package com.example.vertxproject.verticles;

import com.example.vertxproject.model.Article;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpServerVerticleWeb extends AbstractVerticle {
    private HttpServer httpServer = null;

    private Map readingList = new LinkedHashMap();

    @Override
    public void start(Future future) {

        createReadingList();

        Router router = Router.router(vertx);
        // Bind "/" to our hello message - so we are still compatible with out tests.
        /*router.route("/").handler(rc -> {
            rc.response().putHeader("content-type", "text/html")
                         .end("<pre><h1>Hello from my first Vert.x 3 app</h1> </pre> ");
        });*/
        router.route("/assets/*").handler(StaticHandler.create("assets"));
        router.get("/api/articles").handler(this::getAll);

        router.route("/api/articles*").handler(BodyHandler.create());
        router.post("/api/articles").handler(this::addOne);

        router.delete("/api/articles/:id").handler(this::deleteOne);

        router.put("/api/articles/:id").handler(this::updateOne);

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

    private void createReadingList() {
        Article article1 = new Article(
                "Fallacies of distributed computing",
                "https://en.wikipedia.org/wiki/Fallacies_of_distributed_computing");
        readingList.put(article1.getId(), article1);
        Article article2 = new Article(
                "Reactive Manifesto",
                "https://www.reactivemanifesto.org/");
        readingList.put(article2.getId(), article2);
    }

    private void getAll(RoutingContext rc) {
        rc.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(readingList.values()));
    }

    private void addOne(RoutingContext rc) {
        Article article = rc.getBodyAsJson().mapTo(Article.class);
        readingList.put(article.getId(), article);
        rc.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(article));
    }

    private void deleteOne(RoutingContext rc) {
        String id = rc.request().getParam("id");
        try {
            Integer idAsInteger = Integer.valueOf(id);
            readingList.remove(idAsInteger);
            rc.response().setStatusCode(204).end();
        } catch (NumberFormatException e) {
            rc.response().setStatusCode(400).end();
        }
    }

    private void updateOne(RoutingContext rc) {
        String id = rc.request().getParam("id");
        Article newArticle = rc.getBodyAsJson().mapTo(Article.class);
        try {
            Integer idAsInteger = Integer.valueOf(id);
            Article article = (Article)readingList.get(idAsInteger);
            article.setTitle(newArticle.getTitle());
            article.setUrl(newArticle.getUrl());
            rc.response().setStatusCode(201)
                         .putHeader("content-type", "application/json; charset=utf-8")
                         .end(Json.encodePrettily(article));
        } catch (NumberFormatException e) {
            rc.response().setStatusCode(400).end();
        }
    }
}
