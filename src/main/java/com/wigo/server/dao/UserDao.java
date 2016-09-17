package com.wigo.server.dao;

import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class UserDao {
    private static final String GET_USER_SQL = "select id, name from users where id = :userId";
    private static final String GET_USER_BY_EMAIL_SQL = "select id, name from users where email = :email";
    private static final String UPDATE_USER_SQL = "update users set name = :name where email = :email";

    private final RowMapper<UserDto> userDtoMapper = new BeanPropertyRowMapper<>(UserDto.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingColumns("id", "name", "email");
    }

    public UserDto getUser(UUID userId) {
        return jdbcTemplate.query(GET_USER_SQL, new MapSqlParameterSource("userId", userId), userDtoMapper).get(0);
    }

    public UserDto getUserByEmail(String email) {
        List<UserDto> l = jdbcTemplate.query(GET_USER_BY_EMAIL_SQL, new MapSqlParameterSource("email", email), userDtoMapper);
        return l.isEmpty() ? null : l.get(0);
    }

    public UUID createUser(String email, UserDto user) {
        user.setId(UUID.randomUUID());
        insertUser.execute(joinParameterSources(beanParameterSource(user),
                new MapSqlParameterSource("email", email)));
        return user.getId();
    }

    public void updateUserByEmail(String email, UserDto user) {
        if (jdbcTemplate.update(UPDATE_USER_SQL, joinParameterSources(beanParameterSource(user),
                new MapSqlParameterSource("email", email))) == 0)
            throw new DataRetrievalFailureException("User doesn't exist");
    }
}
