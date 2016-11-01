package com.wigo.server;

public class WigoEndpoints {
    public static final String API_URL = "/api";

    public static final String STATUS = "/status";
    public static final String PATCH_STATUS = "/status/{statusId}";
    public static final String MESSAGES_OF_STATUS = "/status/{statusId}/messages";
    public static final String HASHTAGS = "/hashtags";
    public static final String USER = "/user/{userId}";
    public static final String LOGIN = "/login";
}
