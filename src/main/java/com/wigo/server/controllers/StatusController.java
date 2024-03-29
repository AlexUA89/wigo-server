package com.wigo.server.controllers;


import com.wigo.server.WigoEndpoints;
import com.wigo.server.dao.*;
import com.wigo.server.dto.BriefStatusDto;
import com.wigo.server.dto.MessageDto;
import com.wigo.server.dto.StatusDto;
import com.wigo.server.dto.StatusSearchParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.wigo.server.utils.DaoUtils.MIN_INSTANT_STR;

@RestController
@RequestMapping(WigoEndpoints.API_URL)
public class StatusController {

    @Autowired
    private StatusDao statusDao;
    @Autowired
    private MessageDao messageDao;

    @GetMapping(path = WigoEndpoints.STATUS)
    public List<BriefStatusDto> getStatuses(@ModelAttribute StatusSearchParamsDto searchParams) {
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
    public List<MessageDto> getMessages(@PathVariable("statusId") UUID statusId,
                                        @RequestParam(value = "from", defaultValue = MIN_INSTANT_STR) Instant from) {
        return messageDao.getMessages(statusId, from);
    }

    @PostMapping(path = WigoEndpoints.MESSAGES_OF_STATUS)
    public MessageDto postMessage(@PathVariable("statusId") UUID statusId, @RequestBody MessageDto message,
                            @RequestAttribute("userId") UUID userId) {
        message.setUserId(userId);
        message.setCreated(Instant.now());
        return messageDao.getMessage(messageDao.createMessage(statusId, message)).get();
    }

    @GetMapping(path = {WigoEndpoints.HASHTAGS})
    public List<String> getHashtags(@RequestParam("prefix") String prefix, @RequestParam("limit") int limit) {
        return statusDao.getTopHashtags(prefix, limit);
    }
}
