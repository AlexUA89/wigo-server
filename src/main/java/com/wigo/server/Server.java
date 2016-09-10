package com.wigo.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.time.Instant;
import java.util.Date;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        port(args.length < 1 ? 8080 : Integer.parseInt(args[0]));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
        get("/api/status", Server::getStatus, gson::toJson);
        get("/api/status/:id/message", Server::getStatusMessage, gson::toJson);
    }

    private static Object getStatus(Request req, Response res) {
        return ImmutableMap.of("list",
                ImmutableList.of(
                        ImmutableMap.builder().put("id", "er70hserh")
                                .put("latitude", 12.345)
                                .put("longitude", 67.890)
                                .put("name", "hello, world!")
                                .put("text", "good place!!!")
                                .put("start_date", Date.from(Instant.parse("2012-04-23T18:25:43.511Z")))
                                .put("end_date", Date.from(Instant.parse("2012-04-23T18:25:43.511Z")))
                                .put("user", ImmutableMap.of("id", "r0her78er6", "name", "John")).build()));
    }

    private static Object getStatusMessage(Request req, Response res) {
        return ImmutableMap.of("list", ImmutableList.of(ImmutableMap.of(
                "text", "hi!!",
                "user", ImmutableMap.of("id", "r0her78er6", "name", "John"),
                "date", Date.from(Instant.parse("2012-04-23T18:25:43.511Z"))
        )));
    }
}
