package com.soft.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soft.dto.ConnectionRequestDTO;
import com.soft.dto.MyConnRequListDTO;
import com.soft.dto.MySendReqListDTO;
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


    public List<MyConnRequListDTO> getConnectionRequestList(String authemail) {
        String token = authemail.substring(7);
        String email = jwtService.extractUsername(token);
        User loggedInUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email Not register"));
        List<ConnectionRequest> list = connRequestRepository.findByReceiverId(loggedInUser.getId());
        List<MyConnRequListDTO> finaldto = new ArrayList<>();
        for(ConnectionRequest lis : list) {
            MyConnRequListDTO mydto = new MyConnRequListDTO();
            mydto.setRequestId(lis.getId());
            mydto.setSenderId(lis.getSender().getId());
            mydto.setSenderCity(lis.getSender().getCity());
            mydto.setSenderLevel(lis.getSender().getLevel().name());
            mydto.setSenderState(lis.getSender().getState());
            mydto.setSenderTalent(lis.getSender().getTalent());
            mydto.setSenderName(lis.getSender().getName());
            mydto.setStatus(lis.getStatus().name());

            finaldto.add(mydto);
        }
        return finaldto;
    }


    public List<MySendReqListDTO> sentRequestList(String autHeader) {
        String token = autHeader.substring(7);
        String email = jwtService.extractUsername(token);
        User loggedInuser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not registered"));
        List<ConnectionRequest> requests = connRequestRepository.findBySenderId(loggedInuser.getId());
        List<MySendReqListDTO> finaldto = new ArrayList<>();

        for(ConnectionRequest list: requests) {
            MySendReqListDTO dto = new MySendReqListDTO();
            dto.setSentToname(list.getReceiver().getName());
            dto.setSentTocity(list.getReceiver().getCity());
            dto.setSentTolevel(list.getReceiver().getLevel().name());
            dto.setSentTostate(list.getReceiver().getState());
            dto.setSentTostatus(list.getStatus().name());
            dto.setSentTotalent(list.getReceiver().getTalent());
            dto.setSentToid(list.getReceiver().getId());

            finaldto.add(dto);
        }

        return finaldto;
    }

    public String acceptanceOfReqeust(String authHead, Integer requestId, boolean accepts) {
        String token = authHead.substring(7);
        String email = jwtService.extractUsername(token);
        User loggedInuser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not registered"));

        ConnectionRequest request = connRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("No Request Found with this id"));

        if(!request.getReceiver().getId().equals(loggedInuser.getId())) {
            throw new RuntimeException("You are not allowed to act on this Request");
        }

        if(accepts) {
            request.setStatus(RequestStatus.ACCEPTED);
            connRequestRepository.save(request);
            return "Request Accepted";
        } else {
            request.setStatus(RequestStatus.REJECTED);
            connRequestRepository.save(request);
            return "Request Rejected";
        }
    }
    


}
