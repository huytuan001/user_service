package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    @Query(value = "select u from User u where u.status = ?1 ")
    Page<User> getAllUserByStatus(Integer status, Pageable pageable);

    User findByUserName(String userName);
}
