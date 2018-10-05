package com.wigo.server.dao.constants;

public class UserQueries {
    public static final String GET_USER_BY_FB_ID_SQL = "select id, nickname, name, email, fb_id from users where fb_id = :fbId";
    public static final String UPDATE_USER_SQL = "update users set name = :name, nickname = :nickname, email = :email where fb_id = :fbId";
}
