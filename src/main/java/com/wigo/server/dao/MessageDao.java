package com.wigo.server.dao;

import com.wigo.server.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wigo.server.dao.DaoUtils.beanParameterSource;
import static com.wigo.server.dao.DaoUtils.joinParameterSources;
import static com.wigo.server.dao.DaoUtils.toSqlType;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Repository
@Transactional(isolation = READ_COMMITTED)
public class MessageDao {
    private static final String GET_MESSAGES =
            "select messages.id, user_id, nickname, text, created from messages join users on user_id=users.id where ";
    private static final String GET_MESSAGES_SQL = GET_MESSAGES + "status_id = :id and created >= :from";
    private static final String GET_MESSAGE_SQL = GET_MESSAGES + "messages.id = :id";

    private final RowMapper<MessageDto> messageDtoMapper = new BeanPropertyRowMapper<>(MessageDto.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMessage;

    @Autowired
    public MessageDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertMessage = new SimpleJdbcInsert(dataSource).withTableName("messages")
                .usingColumns("id", "user_id", "text", "created", "status_id");
    }

    public List<MessageDto> getMessages(UUID statusId, Instant from) {
        return jdbcTemplate.query(GET_MESSAGES_SQL, new MapSqlParameterSource("id", statusId)
                        .addValue("from", toSqlType(from)), messageDtoMapper);
    }

    public Optional<MessageDto> getMessage(UUID messageId) {
        return jdbcTemplate.query(GET_MESSAGE_SQL, new MapSqlParameterSource("id", messageId), messageDtoMapper)
                .stream().findFirst();
    }

    public UUID createMessage(UUID statusId, MessageDto message) {
        message.setId(UUID.randomUUID());
        insertMessage.execute(joinParameterSources(beanParameterSource(message),
                new MapSqlParameterSource("status_id", statusId)));
        return message.getId();
    }
}
