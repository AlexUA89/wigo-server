package com.wigo.server.controllers;


import com.wigo.server.WigoEndpoints;
import com.wigo.server.dao.MessageDao;
import com.wigo.server.dao.StatusDao;
import com.wigo.server.dao.StatusSearchParams;
import com.wigo.server.dao.UserDao;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import com.wigo.server.dto.UserDto;
import com.wigo.server.errors.StatusNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(WigoEndpoints.API_URL)
public class StatusController {
    private final StatusDao statusDao;
    private final MessageDao messageDao;
    private final UserDao userDao;

    @Autowired
    public StatusController(StatusDao statusDao, MessageDao messageDao, UserDao userDao) {
        this.statusDao = statusDao;
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    @GetMapping(path = WigoEndpoints.STATUS)
    public List<StatusDto> getStatuses(@ModelAttribute StatusSearchParams searchParams) {
        return statusDao.getStatuses(searchParams);
    }

    @GetMapping(path = WigoEndpoints.GET_STATUS)
    public StatusDto getStatus(@PathVariable("statusId") UUID statusId) {
        return statusDao.getStatus(statusId);
    }

    @PostMapping(path = WigoEndpoints.STATUS)
    public UUID postStatus(@RequestBody StatusDto status, @RequestAttribute("userId") UUID userId) {
        status.setUserId(userId);
        return statusDao.createStatus(status);
    }

    @PatchMapping(path = WigoEndpoints.PATCH_STATUS)
    public void patchStatus(@PathVariable("statusId") UUID statusId, @RequestBody StatusDto status,
                            @RequestAttribute("userId") UUID userId) {
        status.setId(statusId);
        status.setUserId(userId);
        statusDao.updateStatus(status);
    }

    @GetMapping(path = WigoEndpoints.MESSAGES_OF_STATUS)
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId) {
        return messageDao.getMessages(statusId);
    }

    @PostMapping(path = WigoEndpoints.MESSAGES_OF_STATUS)
    public UUID postMessage(@PathVariable("statusId") UUID statusId, @RequestBody MessageDto message,
                            @RequestAttribute("userId") UUID userId) {
        message.setUserId(userId);
        message.setCreated(Instant.now());
        return messageDao.createMessage(statusId, message);
    }

    @GetMapping(path = WigoEndpoints.USER)
    public UserDto getUser(@PathVariable("userId") UUID userId) {
        return userDao.getUser(userId);
    }

    @GetMapping(path = {WigoEndpoints.HASHTAGS})
    public List<String> getHashtags(@RequestParam("prefix") String prefix, @RequestParam("limit") int limit) {
        return statusDao.getTopHashtags(prefix, limit);
    }
}
