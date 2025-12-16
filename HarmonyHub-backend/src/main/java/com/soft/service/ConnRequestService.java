//Service Layer for connection Request (Business Logic is Written Here)

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

    //Variable for accessing the methods of USER Repository
    private final UserRepository userRepository;

    //Variable for accessing the methods of ConnectionRequest Repository
    private final ConnRequestRepository connRequestRepository;

    //Variable for accessing the methods of Jwt Services
    private final JwtService jwtService;


    //Contructor Ingetion
    public ConnRequestService(UserRepository userRepository, ConnRequestRepository connRequestRepository,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.connRequestRepository = connRequestRepository;
        this.jwtService = jwtService;
    }
    

    //This Method is Regarding sending the connection-request to another User
    public String sendRequest(String authHeader,ConnectionRequestDTO dto) {

        //Below two lines extract the user email from the JWT token
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);


        //Finds the User(Who is Sending the Request) based On email or else throws the Exception catched by the global Exception Handler
        User sender= userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email Not Registered"));


        //Finds the User(Who is Receiving the Request) Based on Id 
        User receiver=userRepository.findById(dto.getReceiver_id()).orElseThrow(() ->new RuntimeException("Receiver Not Found"));

        //If sender and Receiver Id is same 
        if(sender.getId()==receiver.getId()) {
            throw new RuntimeException("You Cannnot send request to yourself");
        }

        //If there already exists a Connection-Request
        if(connRequestRepository.existsBySenderIdAndReceiverId(sender.getId(), receiver.getId())) {
            throw new RuntimeException("Connection Request Already Send");
        }


        //Building a new Object of connectionRequest
        ConnectionRequest connect = new ConnectionRequest();
        connect.setReceiver(receiver);
        connect.setSender(sender);
        connect.setStatus(RequestStatus.PENDING);
        connRequestRepository.save(connect);
        return "Connection Send Successfully";
    }


    //This method is for returning All the Incomming Request to the User
    public List<MyConnRequListDTO> getConnectionRequestList(String authemail) {


        //Email Extraction via JWT Token
        String token = authemail.substring(7);
        String email = jwtService.extractUsername(token);

        //Finding User on based of email
        User loggedInUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email Not register"));

        //Finding the List of connectionRequest based on loggedInuseId(ie ReceiverID (Database term))
        List<ConnectionRequest> list = connRequestRepository.findByReceiverId(loggedInUser.getId());

        //Initialising empty list of MyConnReqListDTO (Response DTO)
        List<MyConnRequListDTO> finaldto = new ArrayList<>();

        //ForEach Loop
        for(ConnectionRequest lis : list) {

            //Initialising emptyDTO 
            MyConnRequListDTO mydto = new MyConnRequListDTO();

            //Setting the dto with fields in the list
            mydto.setRequestId(lis.getId());
            mydto.setSenderId(lis.getSender().getId());
            mydto.setSenderCity(lis.getSender().getCity());
            mydto.setSenderLevel(lis.getSender().getLevel().name());
            mydto.setSenderState(lis.getSender().getState());
            mydto.setSenderTalent(lis.getSender().getTalent());
            mydto.setSenderName(lis.getSender().getName());
            mydto.setStatus(lis.getStatus().name());

            //At Last adding the current DTO to final List of DTO
            finaldto.add(mydto);
        }
        return finaldto;
    }


    //Method which contains Businnes logic about fetching the Outgoing Request List from (Current Logged In person)
    public List<MySendReqListDTO> sentRequestList(String autHeader) {

        String token = autHeader.substring(7);
        String email = jwtService.extractUsername(token);

        User loggedInuser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not registered"));

        //find By Sender Id because currently a user Is logged in and he is the sender now because he is sendint the request
        //So this method will return list of Users to whom loggedIn User sent the request
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

    //This Method contains the business logic about how the User will accept or reject the incoming request 
    public String acceptanceOfReqeust(String authHead, Integer requestId, boolean accepts) {
        String token = authHead.substring(7);
        String email = jwtService.extractUsername(token);
        User loggedInuser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not registered"));

        ConnectionRequest request = connRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("No Request Found with this id"));

        //This line is very Important to ensure that only the real receiver is able to accept or reject the request
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
