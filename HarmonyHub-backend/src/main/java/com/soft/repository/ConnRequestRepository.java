//JPARepository for dealing with the connection-RequestEntity

package com.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soft.entity.ConnectionRequest;

@Repository
public interface ConnRequestRepository extends JpaRepository<ConnectionRequest,Integer> {

    //Method to check whether their exists a Request with senderId and ReceiverId
    boolean existsBySenderIdAndReceiverId(Integer senderId,Integer receiverId);

    //Method to find the connectionRequest rows based on ReceiverId
    List<ConnectionRequest> findByReceiverId(Integer receiverid);

    //Method to find the connectionRequest rows based on SenderId
    List<ConnectionRequest> findBySenderId(Integer senderid);
}
