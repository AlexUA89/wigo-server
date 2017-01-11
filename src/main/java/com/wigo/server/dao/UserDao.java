package com.wigo.server.dao;

import com.wigo.server.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static com.wigo.server.dao.DaoUtils.beanParameterSource;
import static com.wigo.server.dao.DaoUtils.joinParameterSources;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Repository
@Transactional(isolation = READ_COMMITTED)
public class UserDao {
    private static final String GET_USER_BY_FB_ID_SQL = "select id, nickname, name, email, fb_id from users where fb_id = :fbId";
    private static final String UPDATE_USER_SQL = "update users set name = :name, nickname = :nickname, email = :email where fb_id = :fbId";

    private final RowMapper<UserDto> userDtoMapper = new BeanPropertyRowMapper<>(UserDto.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingColumns("id", "nickname", "name", "email", "fb_id");
    }

    public UserDto getUserByFbId(String fbId) {
        List<UserDto> l = jdbcTemplate.query(GET_USER_BY_FB_ID_SQL, new MapSqlParameterSource("fbId", fbId), userDtoMapper);
        return l.isEmpty() ? null : l.get(0);
    }

    public UUID createUser(UserDto user) {
        user.setId(UUID.randomUUID());
        insertUser.execute(beanParameterSource(user));
        return user.getId();
    }

    public void updateUserByFbId(UserDto user) {
        if (jdbcTemplate.update(UPDATE_USER_SQL, beanParameterSource(user)) == 0)
            throw new DataRetrievalFailureException("User doesn't exist");
    }
}
