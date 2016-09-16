package com.wigo.server.dao;

import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class StatusDao {
    private static final String GET_STATUSES_SQL =
            "select id, user_id, latitude, longitude, name, text, start_date, end_date from statuses";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertStatus;

    @Autowired
    public StatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertStatus = new SimpleJdbcInsert(jdbcTemplate).withTableName("statuses")
                .usingColumns("id", "user_id", "latitude", "longitude", "name", "text", "start_date", "end_date");
    }

    public UUID createStatus(StatusDto status) {
        status.setId(UUID.randomUUID());
        insertStatus.execute(new BeanPropertySqlParameterSource(status));
        return status.getId();
    }

    public List<StatusDto> getStatuses() {
        return jdbcTemplate.query(GET_STATUSES_SQL, statusDtoMapper);
    }

    private final RowMapper<StatusDto> statusDtoMapper = new BeanPropertyRowMapper<>(StatusDto.class);
}
