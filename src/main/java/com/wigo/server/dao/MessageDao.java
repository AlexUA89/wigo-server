package com.wigo.server.dao;

import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MessageDao {
    private static final String GET_MESSAGES_SQL =
            "select id, user_id, text, created from messages where status_id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MessageDto> getMessages(UUID statusId) {
        return jdbcTemplate.query(GET_MESSAGES_SQL, new MapSqlParameterSource("id", statusId), messageDtoMapper);
    }

    private final RowMapper<MessageDto> messageDtoMapper = new BeanPropertyRowMapper<>(MessageDto.class);
}
