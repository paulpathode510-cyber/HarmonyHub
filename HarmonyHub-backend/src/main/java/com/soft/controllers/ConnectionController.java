//This Controller Class is for handling all the operation of users Regarding the connection-requests (Only Users can access this Methods)
//Admins are not allowed for now 

package com.soft.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.dto.ConnectionRequestDTO;
import com.soft.service.ConnRequestService;

@Controller
@RequestMapping("/connection")
public class ConnectionController {

    //Variable for accessing the methods of connectionRequestService
    private final ConnRequestService connRequestService;

    //contructor Ingestion
    public ConnectionController(ConnRequestService connRequestService) {
        this.connRequestService = connRequestService;
    }

    //This Endpoint is for sending request for connection from one user to another
    @PostMapping("/connection-request")
    public ResponseEntity<?> connectionRequest(@RequestHeader("Authorization") String authHead,@RequestBody ConnectionRequestDTO dto) {
        return ResponseEntity.ok(connRequestService.sendRequest(authHead, dto));
    }

    //This Endpoint is for fetching all the Incoming request from the other user
    @GetMapping("/connection-request-list")
    public ResponseEntity<?> getMyConnRequestList(@RequestHeader("Authorization") String authHead) {
        return ResponseEntity.ok(connRequestService.getConnectionRequestList(authHead));
    }
    
    //This Endpoint is for fetching all the Outgoing request from the loggedIn user
    @GetMapping("/coonection-sentrequest-list")
    public ResponseEntity<?> getMySentRequestsList(@RequestHeader("Authorization") String authHead) {
        return ResponseEntity.ok(connRequestService.sentRequestList(authHead));
    }

    //This Endpoint is for accepting or rejecting the incoming request by loggedIn user
    @PutMapping("/connection-accepts/{requestId}")
    public ResponseEntity<?> requestAcceptance(@RequestHeader("Authorization") String authHead,@PathVariable Integer requestId,@RequestParam boolean accepts) {
        return ResponseEntity.ok(connRequestService.acceptanceOfReqeust(authHead, requestId, accepts));
    }

}
