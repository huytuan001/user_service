package com.example.demo.controller;

import com.example.demo.model.ReqChangePassword;
import com.example.demo.model.ReqCreateUser;
import com.example.demo.model.ResUserData;
import com.example.demo.model.SimpleResponse;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = {"", "${version.api.v1}"})
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<Page<ResUserData>> getAllUsers(@RequestParam Integer status, @ApiParam Pageable pageable) {
        Page<ResUserData> page = userService.getAllUser(status, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public SimpleResponse createNewUser(@Valid @RequestBody ReqCreateUser reqCreateUser) {
        return userService.createNewUser(reqCreateUser);
    }

    @PutMapping(value = "/users/update")
    @ResponseBody
    public SimpleResponse updateUser(@Valid @RequestBody ReqCreateUser reqCreateUser) {
        return userService.updateUser(reqCreateUser);
    }

    @PostMapping(value = "/change-password")
    @ResponseBody
    public SimpleResponse changePassword(@Valid @RequestBody ReqChangePassword reqChangePassword) {
        return userService.changePass(reqChangePassword);
    }
}
