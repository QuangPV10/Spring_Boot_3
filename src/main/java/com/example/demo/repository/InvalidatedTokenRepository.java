package com.example.demo.repository;

import com.example.demo.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

    @Query(value = "SELECT t FROM InvalidatedToken t WHERE t.expiryTime = (SELECT MIN(t2.expiryTime) FROM InvalidatedToken t2)")
    List<InvalidatedToken> findAllByMinExpiryTime();
}
