package com.wigo.server.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Created by AlexUA89 on 3/13/2017.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such status")
public class StatusAlreadyExistException extends RuntimeException {

    private UUID statusId;

    public StatusAlreadyExistException(UUID statusId) {
        super("Status already exist with ID: " + statusId);
        this.statusId = statusId;
    }
}
