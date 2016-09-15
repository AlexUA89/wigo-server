package com.wigo.server.controllers;


import com.wigo.server.WigoEndpionts;
import com.wigo.server.dao.MessageDao;
import com.wigo.server.dao.StatusDao;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import com.wigo.server.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(path = WigoEndpionts.GET_STATUSES_STUB, method = RequestMethod.GET)
    public List<StatusDto> getStatusSTUB() {
        return statusDao.getStatuses();
    }

    @RequestMapping(path = WigoEndpionts.GET_MESSAGES_OF_STATUS_STUB, method = RequestMethod.GET)
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId) {
        return messageDao.getMessages(statusId);
    }


}
