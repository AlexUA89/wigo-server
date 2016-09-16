package com.wigo.server.controllers;


import com.wigo.server.WigoEndpionts;
import com.wigo.server.dao.MessageDao;
import com.wigo.server.dao.StatusDao;
import com.wigo.server.dao.StatusSearchParams;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(WigoEndpionts.API_URL)
public class StatusController {
    private final StatusDao statusDao;

    private final MessageDao messageDao;

    @Autowired
    public StatusController(StatusDao statusDao, MessageDao messageDao) {
        this.statusDao = statusDao;
        this.messageDao = messageDao;
    }

    @GetMapping(path = WigoEndpionts.STATUS)
    public List<StatusDto> getStatuses(@ModelAttribute StatusSearchParams searchParams) {
        return statusDao.getStatuses(searchParams);
    }

    @GetMapping(path = WigoEndpionts.MESSAGES_OF_STATUS)
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId) {
        return messageDao.getMessages(statusId);
    }

    @PutMapping(path = WigoEndpionts.STATUS)
    public UUID putStatus(@RequestBody StatusDto status) {
        return statusDao.createStatus(status);
    }


}
