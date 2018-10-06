package com.wigo.server.dao;

import com.wigo.server.dao.constants.CategoryQueries;
import com.wigo.server.dto.CategoryDto;
import com.wigo.server.errors.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class CategoryDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<CategoryDto> categoryDtoMapper = new BeanPropertyRowMapper<>(CategoryDto.class);

    @Autowired
    public CategoryDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public CategoryDto getCategoryById(UUID id) {
        List<CategoryDto> result;
        try {
            result = jdbcTemplate.query(CategoryQueries.GET_CATEGORY_BY_ID, new MapSqlParameterSource("id", id), categoryDtoMapper);
        } catch (IllegalArgumentException e) {
            throw new CategoryNotFoundException(id);
        }
        if (!result.isEmpty()) {
            return result.get(0);
        }
        throw new CategoryNotFoundException(id);
    }

    public List<CategoryDto> getCategoriesList() {
        return jdbcTemplate.query(CategoryQueries.GET_CATEGORIES_LIST, categoryDtoMapper);
    }


}
