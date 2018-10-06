package com.wigo.server.dao;

import com.wigo.server.dao.constants.UserQueries;
import com.wigo.server.domain.User;
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

import static com.wigo.server.utils.DaoUtils.beanParameterSource;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Repository
@Transactional(isolation = READ_COMMITTED)
public class UserDao {

    private final RowMapper<User> userDtoMapper = new BeanPropertyRowMapper<>(User.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingColumns("id", "nickname", "name", "email", "fb_id");
    }

    public User getUserByFbId(String fbId) {
        List<User> l = jdbcTemplate.query(UserQueries.GET_USER_BY_FB_ID_SQL, new MapSqlParameterSource("fbId", fbId), userDtoMapper);
        return l.isEmpty() ? null : l.get(0);
    }

    public UUID createUser(User user) {
        user.setId(UUID.randomUUID());
        insertUser.execute(beanParameterSource(user));
        return user.getId();
    }

    public void updateUserByFbId(User user) {
        if (jdbcTemplate.update(UserQueries.UPDATE_USER_SQL, beanParameterSource(user)) == 0)
            throw new DataRetrievalFailureException("User doesn't exist");
    }
}
