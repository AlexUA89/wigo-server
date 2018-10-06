package com.wigo.server.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such category")
public class CategoryNotFoundException extends RuntimeException  {

    private UUID category_id;

    public CategoryNotFoundException(UUID category_id) {
        super("Status already exist with ID: " + category_id);
        this.category_id = category_id;
    }

}
