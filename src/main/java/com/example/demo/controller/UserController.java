package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.UserService;
import io.swagger.annotations.*;
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
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "page", value = "page number start from 0", dataType = "integer",
                            examples = @Example(@ExampleProperty("1")), paramType = "query"),
                    @ApiImplicitParam(name = "size", value = "maximum number of item in page", dataType = "integer",
                            examples = @Example(@ExampleProperty("40")), paramType = "query"),
            }
    )
    public ResponseEntity<Page<ResUserData>> getAllUsers(@RequestParam Integer status, @ApiParam Pageable pageable) {
        Page<ResUserData> page = userService.getAllUser(status, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userName}")
    @ResponseBody
    public ResUpdateNewUser findOneByUserName(@PathVariable("userName") String userName) {
        return userService.getUserByUserName(userName);
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
