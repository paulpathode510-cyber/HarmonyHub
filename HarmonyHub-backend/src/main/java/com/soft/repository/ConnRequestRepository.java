package com.soft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soft.entity.ConnectionRequest;

@Repository
public interface ConnRequestRepository extends JpaRepository<ConnectionRequest,Integer> {
    boolean existsBySenderIdAndReceiverId(Integer senderId,Integer receiverId);
}
