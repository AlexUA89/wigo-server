package com.wigo.server.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

public class DaoUtils {
    public static Instant MIN_INSTANT = Instant.parse("1900-01-01T00:00:00Z");
    public static Instant MAX_INSTANT = Instant.parse("2100-01-01T00:00:00Z");

    public static BeanPropertySqlParameterSource beanParameterSource(Object o) {
        return new BeanPropertySqlParameterSource(o) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object v = super.getValue(paramName);
                return v instanceof Instant ? Timestamp.from((Instant) v) :
                        v instanceof Enum ? v.toString() :
                        v instanceof URL ? v.toString() : v;
            }
        };
    }

    public static SqlParameterSource joinParameterSources(SqlParameterSource... a) {
        return new SqlParameterSource() {
            SqlParameterSource getSourceNull(String s) {
                for (SqlParameterSource x : a)
                    if (x.hasValue(s))
                        return x;
                return null;
            }

            SqlParameterSource getSource(String s) {
                SqlParameterSource ss = getSourceNull(s);
                if (ss == null)
                    throw new IllegalArgumentException("Parameter not found: " + s);
                return ss;
            }

            public boolean hasValue(String s) {
                return getSourceNull(s) != null;
            }

            public Object getValue(String s) throws IllegalArgumentException {
                return getSource(s).getValue(s);
            }

            public int getSqlType(String s) {
                return getSource(s).getSqlType(s);
            }

            public String getTypeName(String s) {
                return getSource(s).getTypeName(s);
            }
        };
    }

    private static final Pattern LIKE_CHARS = Pattern.compile("[\\[%_\\\\]");

    public static String escapeLike(String s) {
        return LIKE_CHARS.matcher(s).replaceAll("\\\\1");
    }
}
