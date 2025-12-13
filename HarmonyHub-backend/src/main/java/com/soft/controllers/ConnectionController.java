package com.soft.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soft.dto.ConnectionRequestDTO;
import com.soft.service.ConnRequestService;

@Controller
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnRequestService connRequestService;

    public ConnectionController(ConnRequestService connRequestService) {
        this.connRequestService = connRequestService;
    }

    @PostMapping("/connection-request")
    public ResponseEntity<?> connectionRequest(@RequestHeader("Authorization") String authHead,@RequestBody ConnectionRequestDTO dto) {
        return ResponseEntity.ok(connRequestService.sendRequest(authHead, dto));
    }

    @GetMapping("/connection-request-list")
    public ResponseEntity<?> getMyConnRequestList(@RequestHeader("Authorization") String authHead) {
        return ResponseEntity.ok(connRequestService.getConnectionRequestList(authHead));
    }
    
    @GetMapping("/coonection-sentrequest-list")
    public ResponseEntity<?> getMySentRequestsList(@RequestHeader("Authorization") String authHead) {
        return ResponseEntity.ok(connRequestService.sentRequestList(authHead));
    }
}
