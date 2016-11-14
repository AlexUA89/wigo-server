package com.wigo.server.dao;

import com.wigo.server.dto.StatusDto;
import com.wigo.server.dto.UserDto;
import com.wigo.server.errors.StatusNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static com.wigo.server.dao.DaoUtils.beanParameterSource;
import static com.wigo.server.dao.DaoUtils.escapeLike;
import static java.util.Collections.disjoint;
import static java.util.stream.Collectors.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Repository
@Transactional(isolation = READ_COMMITTED)
public class StatusDao {
    private static final String GET_STATUSES_SQL =
            "select id, user_id, latitude, longitude, name, text, url, start_date, end_date, kind from statuses " +
                    "where latitude between :startLatitude and :endLatitude and " +
                    "longitude between :startLongitude and :endLongitude and " +
                    "(start_date < :endDate or start_date is null) and (end_date > :startDate or end_date is null) " +
                    "and (to_tsvector('english', name || '\\n'|| text) @@ plainto_tsquery(:search) or :search is null)";
    private static final String GET_STATUS_SQL =
            "select id, user_id, latitude, longitude, name, text, url, start_date, end_date, kind from statuses " +
                    "where id = :id";
    private static final String UPDATE_STATUS_SQL =
            "update statuses set latitude = :latitude, longitude = :longitude, name = :name, text = :text, url = :url" +
                    "start_date = :startDate, end_date = :endDate, kind = :kind where id = :id and user_id = :userId";
    private static final String DELETE_HASHTAGS_SQL = "delete from status_hashtags where status_id = :id";
    private static final String GET_HASHTAGS_SQL =
            "select status_id, hashtag from status_hashtags where status_id in (:ids)";
    private static final String GET_TOP_HASHTAGS_SQL =
            "select hashtag from status_hashtags where hashtag like :prefix || '%' " +
                    "group by hashtag order by count(*) desc limit :limit";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertStatus;
    private final SimpleJdbcInsert insertHashtags;

    @Autowired
    public StatusDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertStatus = new SimpleJdbcInsert(dataSource).withTableName("statuses")
                .usingColumns("id", "user_id", "latitude", "longitude", "name", "text", "url", "start_date", "end_date",
                        "kind");
        insertHashtags = new SimpleJdbcInsert(dataSource).withTableName("status_hashtags")
                .usingColumns("status_id", "hashtag");
    }

    public UUID createStatus(StatusDto status) {
        status.setId(UUID.randomUUID());
        insertStatus.execute(beanParameterSource(status));
        insertHashtags(status);
        return status.getId();
    }

    private void insertHashtags(StatusDto status) {
        insertHashtags.executeBatch(status.getHashtags().stream()
                .map(h -> beanParameterSource(new StatusHashtag(status.getId(), h)))
                .toArray(SqlParameterSource[]::new));
    }

    public void updateStatus(StatusDto status) {
        // TODO: exract permission check from dao
        if (jdbcTemplate.update(UPDATE_STATUS_SQL, beanParameterSource(status)) == 0)
            throw new DataRetrievalFailureException("Status doesn't exist or belongs to another user");
        jdbcTemplate.update(DELETE_HASHTAGS_SQL, beanParameterSource(status));
        insertHashtags(status);
    }

    public StatusDto getStatus(UUID statusId) {
        List<StatusDto> result = jdbcTemplate.query(GET_STATUS_SQL, new MapSqlParameterSource("id", statusId), statusDtoMapper);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        throw new StatusNotFoundExeption();
    }

    public List<StatusDto> getStatuses(StatusSearchParams searchParams) {
        List<StatusDto> res = jdbcTemplate.query(GET_STATUSES_SQL, beanParameterSource(searchParams), statusDtoMapper);
        if (!res.isEmpty()) {
            Map<UUID, Set<String>> hashtags = jdbcTemplate.query(GET_HASHTAGS_SQL,
                    new MapSqlParameterSource("ids", res.stream().map(StatusDto::getId).collect(Collectors.toSet())),
                    statusHashtagMapper).stream().collect(
                    groupingBy(StatusHashtag::getStatusId, mapping(StatusHashtag::getHashtag, toSet())));
            res.stream().forEach(s -> s.setHashtags(hashtags.getOrDefault(s.getId(), s.getHashtags())));
        }
        if (searchParams.getHashtags() != null)
            res.removeIf(s -> disjoint(s.getHashtags(), searchParams.getHashtags()));
        return res;
    }

    public List<String> getTopHashtags(String prefix, int limit) {
        return jdbcTemplate.queryForList(GET_TOP_HASHTAGS_SQL,
                new MapSqlParameterSource("prefix", escapeLike(prefix)).addValue("limit", limit),
                String.class);
    }

    private final RowMapper<StatusDto> statusDtoMapper = new BeanPropertyRowMapper<>(StatusDto.class);

    private final RowMapper<StatusHashtag> statusHashtagMapper = new BeanPropertyRowMapper<>(StatusHashtag.class);

    private static class StatusHashtag {
        private UUID statusId;

        private String hashtag;

        public StatusHashtag() {
        }

        public StatusHashtag(UUID statusId, String hashtag) {
            this.statusId = statusId;
            this.hashtag = hashtag;
        }

        public UUID getStatusId() {
            return statusId;
        }

        public void setStatusId(UUID statusId) {
            this.statusId = statusId;
        }

        public String getHashtag() {
            return hashtag;
        }

        public void setHashtag(String hashtag) {
            this.hashtag = hashtag;
        }
    }
}
