package com.soft.service;

import org.springframework.stereotype.Service;

import com.soft.dto.ConnectionRequestDTO;
import com.soft.entity.ConnectionRequest;
import com.soft.entity.User;
import com.soft.enums.RequestStatus;
import com.soft.repository.ConnRequestRepository;
import com.soft.repository.UserRepository;

@Service
public class ConnRequestService {
    private final UserRepository userRepository;
    private final ConnRequestRepository connRequestRepository;
    private final JwtService jwtService;
    public ConnRequestService(UserRepository userRepository, ConnRequestRepository connRequestRepository,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.connRequestRepository = connRequestRepository;
        this.jwtService = jwtService;
    }
    
    public String sendRequest(String authHeader,ConnectionRequestDTO dto) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);
        User sender= userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email Not Registered"));
        User receiver=userRepository.findById(dto.getReceiver_id()).orElseThrow(() ->new RuntimeException("Receiver Not Found"));
        if(sender.getId()==receiver.getId()) {
            throw new RuntimeException("You Cannnot send request to yourself");
        }
        if(connRequestRepository.existsBySenderIdAndReceiverId(sender.getId(), receiver.getId())) {
            throw new RuntimeException("Connection Request Already Send");
        }
        ConnectionRequest connect = new ConnectionRequest();
        connect.setReceiver(receiver);
        connect.setSender(sender);
        connect.setStatus(RequestStatus.PENDING);
        connRequestRepository.save(connect);
        return "Connection Send Successfully";
    }
}
