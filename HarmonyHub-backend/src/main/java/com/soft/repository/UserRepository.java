package com.soft.repository;

import java.util.Optional;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soft.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>  {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("""
            SELECT u FROM User u
           WHERE (:talent IS NULL OR u.talent = :talent)
             AND (:city IS NULL OR u.city = :city)
            """)
            Page<User> findUser(@Param("talent") String talent,@Param("city") String city,Pageable pageable);
}
