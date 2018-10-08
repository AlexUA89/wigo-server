package com.wigo.server.controllers;

import com.wigo.server.WigoEndpoints;
import com.wigo.server.dao.CategoryDao;
import com.wigo.server.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(WigoEndpoints.API_URL)
public class CategoryController {
    public static final String DOC_URL = "/category";

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping(DOC_URL + "/list")
    public List<CategoryDto> getcategoriesList() {
        return categoryDao.getCategoriesList();
    }

    @GetMapping(DOC_URL + "/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable("categoryId") UUID categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }


}
