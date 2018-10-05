package com.wigo.server.dao;

import com.wigo.server.dao.constants.MessageQueries;
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

import static com.wigo.server.utils.DaoUtils.beanParameterSource;
import static com.wigo.server.utils.DaoUtils.joinParameterSources;
import static com.wigo.server.utils.DaoUtils.toSqlType;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Repository
@Transactional(isolation = READ_COMMITTED)
public class MessageDao {

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
        return jdbcTemplate.query(MessageQueries.GET_MESSAGES_SQL, new MapSqlParameterSource("id", statusId)
                        .addValue("from", toSqlType(from)), messageDtoMapper);
    }

    public Optional<MessageDto> getMessage(UUID messageId) {
        return jdbcTemplate.query(MessageQueries.GET_MESSAGE_SQL, new MapSqlParameterSource("id", messageId), messageDtoMapper)
                .stream().findFirst();
    }

    public UUID createMessage(UUID statusId, MessageDto message) {
        message.setId(UUID.randomUUID());
        insertMessage.execute(joinParameterSources(beanParameterSource(message),
                new MapSqlParameterSource("status_id", statusId)));
        return message.getId();
    }
}
