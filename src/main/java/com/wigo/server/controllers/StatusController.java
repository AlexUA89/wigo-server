package com.wigo.server.controllers;


import com.wigo.server.WigoEndpionts;
import com.wigo.server.dao.MessageDao;
import com.wigo.server.dao.StatusDao;
import com.wigo.server.dao.StatusSearchParams;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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

    @PostMapping(path = WigoEndpionts.STATUS)
    public UUID postStatus(@RequestBody StatusDto status) {
        // TODO: user from session
        return statusDao.createStatus(status);
    }

    @PatchMapping(path = WigoEndpionts.PATCH_STATUS)
    public void patchStatus(@PathVariable("statusId") UUID statusId, @RequestBody StatusDto status) {
        status.setId(statusId);
        // TODO: user from session
        statusDao.updateStatus(status);
    }

    @GetMapping(path = WigoEndpionts.MESSAGES_OF_STATUS)
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId) {
        return messageDao.getMessages(statusId);
    }

    @PostMapping(path = WigoEndpionts.MESSAGES_OF_STATUS)
    public UUID postMessage(@PathVariable("statusId") UUID statusId, @RequestBody MessageDto message) {
        // TODO: user from session
        message.setCreated(Instant.now());
        return messageDao.createMessage(statusId, message);
    }
}
