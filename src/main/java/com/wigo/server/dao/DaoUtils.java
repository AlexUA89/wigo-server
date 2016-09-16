package com.wigo.server.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * Created by alyaxey on 9/16/16.
 */
public class DaoUtils {
    public static Instant MIN_INSTANT = Instant.parse("1900-01-01T00:00:00Z");
    public static Instant MAX_INSTANT = Instant.parse("2100-01-01T00:00:00Z");

    public static BeanPropertySqlParameterSource beanParameterSource(Object o) {
        return new BeanPropertySqlParameterSource(o) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object v = super.getValue(paramName);
                return v instanceof Instant ? Timestamp.from((Instant) v) : v;
            }
        };
    }
}
