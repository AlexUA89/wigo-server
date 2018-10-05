package com.wigo.server.dao.constants;

public class CategoryQueries {
    public static final String GET_CATEGORY_BY_ID = "select id, name, image_url, parent_id from categories where id = :id";
    public static final String GET_CATEGORIES_LIST = "select id, name, image_url, parent_id from categories";
}
