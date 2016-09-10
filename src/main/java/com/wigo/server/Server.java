package com.wigo.server;

import com.google.gson.Gson;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        Gson gson = new Gson();
        get("/api/status", (req, res) -> {return ""+req.queryParams("latitude_from");}, gson::toJson);
    }
}
