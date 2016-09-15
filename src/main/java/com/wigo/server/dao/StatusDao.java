package com.wigo.server.dao;

import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StatusDao {
    private static final String GET_USERS_SQL = "select id, user_id, latitude, longitude, name, text, start_date, end_date from statuses";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StatusDto> getStatuses() {
        return jdbcTemplate.query(GET_USERS_SQL, statusDtoMapper);
    }

    private static final RowMapper<StatusDto> statusDtoMapper = (r, i) -> new StatusDto(UUID.fromString(r.getString(1)), UUID.fromString(r.getString(2)), r.getDouble(3), r.getDouble(4), r.getString(5), r.getString(6), r.getTimestamp(7).toInstant(), r.getTimestamp(8).toInstant());
}
