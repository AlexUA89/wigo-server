package com.wigo.server.dao;

import com.wigo.server.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MessageDao {
    private static final String GET_MESSAGES_SQL = "select id, user_id, text, created from messages where status_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MessageDto> getMessages(UUID statusId) {
        return jdbcTemplate.query(GET_MESSAGES_SQL, new Object[]{statusId}, messageDtoMapper);
    }

    private static final RowMapper<MessageDto> messageDtoMapper = (r, i) -> new MessageDto(UUID.fromString(r.getString(1)), UUID.fromString(r.getString(2)), r.getString(3), r.getTimestamp(4).toInstant());
}
