package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface UserService extends CrudService<User, BigInteger> {
    Page<ResUserData> getAllUser(Integer status, Pageable pageable);

    SimpleResponse createNewUser(ReqCreateUser reqCreateUser);

    SimpleResponse updateUser(ReqCreateUser reqCreateUser);

    SimpleResponse changePass(ReqChangePassword reqChangePassword);

    ResUpdateNewUser getUserByUserName(String userName);
}
