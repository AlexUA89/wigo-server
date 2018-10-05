package com.wigo.server.dao.constants;

public class StatusQueries {

    public static final String GET_STATUSES_SQL =
            "select id, latitude, longitude, name, category_id, start_date, end_date from statuses " +
                    "where latitude between :startLatitude and :endLatitude and " +
                    "longitude between :startLongitude and :endLongitude and " +
                    "(start_date < :endDate or start_date is null) and (end_date > :startDate or end_date is null) " +
                    "and (to_tsvector('english', name || '\\n'|| text) @@ plainto_tsquery(:search) or :search is null) " +
                    "and (CAST(:url AS varchar) is null or url = :url)";
    public static final String GET_STATUS_SQL =
            "select id, user_id, latitude, longitude, name, text, url, start_date, end_date, category_id from statuses " +
                    "where id = :id";
    public static final String GET_STATUS_BY_NAME_SQL =
            "select id, user_id, latitude, longitude, name, text, url, start_date, end_date, category_id from statuses " +
                    "where name = :name";
    public static final String UPDATE_STATUS_SQL =
            "update statuses set latitude = :latitude, longitude = :longitude, name = :name, text = :text, url = :url, " +
                    "start_date = :startDate, end_date = :endDate, category_id = :category_id where id = :id and user_id = :userId";
    public static final String DELETE_HASHTAGS_SQL = "delete from status_hashtags where status_id = :id";
    public static final String GET_HASHTAGS_SQL =
            "select status_id, hashtag from status_hashtags where status_id in (:ids)";
    public static final String GET_TOP_HASHTAGS_SQL =
            "select hashtag from status_hashtags where hashtag like :prefix || '%' " +
                    "group by hashtag order by count(*) desc limit :limit";
    public static final String GET_IMAGES_SQL =
            "select status_id, url from status_images where status_id in (:ids)";
    public static final String DELETE_IMAGES_SQL = "delete from status_images where status_id = :id";

}
