package com.wigo.server.dao;

import com.wigo.server.dto.CategoryDto;
import com.wigo.server.dto.StatusDto;
import com.wigo.server.errors.CategoryNotFoundException;
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

@Repository
@Transactional
public class CategoryDao {

    private static final String GET_CATEGORY_BY_ID =
            "select id, name, image_url, parent_id from categories where id = :id";

    private static final String GET_CATEGORIES_LIST =
            "select id, name, image_url, parent_id from categories";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<CategoryDto> categoryDtoMapper = new BeanPropertyRowMapper<>(CategoryDto.class);


    public CategoryDto getCategoryById(UUID id) {
        List<CategoryDto> result = jdbcTemplate.query(GET_CATEGORY_BY_ID, new MapSqlParameterSource("id", id), categoryDtoMapper);
        if(!result.isEmpty()){
            return result.get(0);
        }
        throw new CategoryNotFoundException();
    }

    public List<CategoryDto> getCategoriesList() {
        return jdbcTemplate.query(GET_CATEGORIES_LIST, categoryDtoMapper);
    }


    public CategoryDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
