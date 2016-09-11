package com.wigo.server.controllers;


import com.wigo.server.WigoEndpionts;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import com.wigo.server.dto.UserDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(WigoEndpionts.API_URL)
public class StatusController {

    @RequestMapping(path = WigoEndpionts.GET_STATUSES_STUB, method = RequestMethod.GET)
    public List<StatusDto> getStatusSTUB() {
        UserDto user = new UserDto(UUID.randomUUID(), "John");
        StatusDto status = new StatusDto(UUID.randomUUID(), 12.345, 67.890, "hello, world!", "good place!!!", user, Instant.now(), Instant.now());
        return Collections.singletonList(status);
    }

    @RequestMapping(path = WigoEndpionts.GET_MESSAGES_OF_STATUS_STUB, method = RequestMethod.GET)
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId) {
        UserDto user = new UserDto(UUID.randomUUID(), "John");
        MessageDto messageDto = new MessageDto("Hi!!!", user, Instant.now());
        return Collections.singletonList(messageDto);
    }


}
