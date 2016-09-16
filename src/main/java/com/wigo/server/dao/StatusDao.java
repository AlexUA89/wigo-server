package com.wigo.server.dao;

import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    private static final String UPDATE_STATUS_SQL =
            "update statuses set latitude = :latitude, longitude = :longitude, name = :name, text = :text, " +
                    "start_date = :startDate, end_date = :endDate where id = :id and user_id = :userId";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertStatus;

    @Autowired
    public StatusDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertStatus = new SimpleJdbcInsert(dataSource).withTableName("statuses")
                .usingColumns("id", "user_id", "latitude", "longitude", "name", "text", "start_date", "end_date");
    }

    public UUID createStatus(StatusDto status) {
        status.setId(UUID.randomUUID());
        insertStatus.execute(beanParameterSource(status));
        return status.getId();
    }

    public void updateStatus(StatusDto status) {
        if (jdbcTemplate.update(UPDATE_STATUS_SQL, beanParameterSource(status)) == 0)
            throw new DataRetrievalFailureException("Status doesn't exist or belongs to another user");
    }

    public List<StatusDto> getStatuses(StatusSearchParams searchParams) {
        return jdbcTemplate.query(GET_STATUSES_SQL, beanParameterSource(searchParams), statusDtoMapper);
    }

    private final RowMapper<StatusDto> statusDtoMapper = new BeanPropertyRowMapper<>(StatusDto.class);
}
