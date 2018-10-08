package com.wigo.server.dao.constants;

public class MessageQueries {
    private static final String GET_MESSAGES =
            "select messages.id, user_id, nickname, text, created from messages join users on user_id=users.id where ";
    public static final String GET_MESSAGE_SQL = GET_MESSAGES + "messages.id = :id";
    public static final String GET_MESSAGES_SQL = GET_MESSAGES + "status_id = :id and created >= :from";
}
