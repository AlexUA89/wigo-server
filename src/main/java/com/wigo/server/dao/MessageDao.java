package com.wigo.server.dao;

import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static com.wigo.server.dao.DaoUtils.beanParameterSource;
import static com.wigo.server.dao.DaoUtils.joinParameterSources;

@Repository
public class MessageDao {
    private static final String GET_MESSAGES_SQL =
            "select id, user_id, text, created from messages where status_id = :id";

    private final RowMapper<MessageDto> messageDtoMapper = new BeanPropertyRowMapper<>(MessageDto.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMessage;

    @Autowired
    public MessageDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertMessage = new SimpleJdbcInsert(dataSource).withTableName("messages")
                .usingColumns("id", "user_id", "text", "created", "status_id");
    }

    public List<MessageDto> getMessages(UUID statusId) {
        return jdbcTemplate.query(GET_MESSAGES_SQL, new MapSqlParameterSource("id", statusId), messageDtoMapper);
    }

    public UUID createMessage(UUID statusId, MessageDto message) {
        message.setId(UUID.randomUUID());
        insertMessage.execute(joinParameterSources(beanParameterSource(message),
                new MapSqlParameterSource("status_id", statusId)));
        return message.getId();
    }
}
