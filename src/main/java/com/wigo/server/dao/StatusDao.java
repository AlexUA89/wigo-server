package com.wigo.server.dao;

import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.wigo.server.dao.DaoUtils.beanParameterSource;

@Repository
public class StatusDao {
    private static final String GET_STATUSES_SQL =
            "select id, user_id, latitude, longitude, name, text, start_date, end_date from statuses " +
                    "where latitude between :startLatitude and :endLatitude and " +
                    "longitude between :startLongitude and :endLongitude and " +
                    "start_date < :endDate and end_date > :startDate";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertStatus;

    @Autowired
    public StatusDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
        insertStatus = new SimpleJdbcInsert(jdbcTemplate).withTableName("statuses")
                .usingColumns("id", "user_id", "latitude", "longitude", "name", "text", "start_date", "end_date");
    }

    public UUID createStatus(StatusDto status) {
        status.setId(UUID.randomUUID());
        insertStatus.execute(beanParameterSource(status));
        return status.getId();
    }

    public List<StatusDto> getStatuses(StatusSearchParams searchParams) {
        return jdbcTemplate.query(GET_STATUSES_SQL, beanParameterSource(searchParams), statusDtoMapper);
    }

    private final RowMapper<StatusDto> statusDtoMapper = new BeanPropertyRowMapper<>(StatusDto.class);
}
