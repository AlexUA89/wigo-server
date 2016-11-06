package com.wigo.server.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by AlexUA89 on 11/6/2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such status")
public class StatusNotFoundExeption extends RuntimeException {
}
