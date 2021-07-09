package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.i18n.Messages;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.ConfigProperties;
import com.example.demo.utils.ContextHelper;
import com.example.demo.utils.PasswordUtils;
import com.example.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends CrudServiceImpl<User, BigInteger> implements UserService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ConfigProperties configurationProperties;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Page<ResUserData> getAllUser(Integer status, Pageable pageable) {
        Page<User> allByStatusTrue = userRepository.getAllUserByStatus(status, pageable);
        List<ResUserData> collect = allByStatusTrue.stream().map(el -> modelMapper.map(el, ResUserData.class)).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, allByStatusTrue.getTotalElements());
    }

    @Override
    public ResUpdateNewUser getUserByUserName(String userName) {
        ResUpdateNewUser res = new ResUpdateNewUser();
        try {
            User user = userRepository.findByUserName(userName);
            if (user != null) {
                ResUserData data = modelMapper.map(user,ResUserData.class);
                res.setData(data);
                res.setReturnCode(Utils.ReturnCode.SUCCESS.getValue());
                res.setReturnMsg(messageSource.getMessage(Messages.INFO_SUCCESS, null, ContextHelper.getLocale()));
            } else {
                res.setReturnCode(Utils.ReturnCode.FAIL.getValue());
                res.setReturnMsg(messageSource.getMessage(Messages.ERROR_USER_NOT_FOUND, null, ContextHelper.getLocale()));
                return res;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            res.setReturnCode(Utils.ReturnCode.FAIL.getValue());
            res.setReturnMsg(messageSource.getMessage(Messages.ERROR_EXCEPTION_UNEXPECTED, null, ContextHelper.getLocale()));
            return res;
        }
        return res;
    }

    @Override
    public SimpleResponse createNewUser(ReqCreateUser reqCreateUser) {
        SimpleResponse simpleResponse = new SimpleResponse();
        try {
            String userName = reqCreateUser.getUserName();
            if (checkUserExisted(userName)) {
                simpleResponse.setReturnMsg(messageSource.getMessage(Messages.WARN_USER_NAME_EXIST, null, ContextHelper.getLocale()));
                simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
                return simpleResponse;
            }
            User user = new User();
            user.setUserName(reqCreateUser.getUserName());
            user.setPassword(PasswordUtils.generatePasswordHash(reqCreateUser.getPassword()));
            user.setStatus(Utils.UserStatus.NOT_ACTIVE.getValue());
            Timestamp timeNow = Utils.getTimeNow(configProperties.getFormatStandardDate());
            user.setCreatedDate(timeNow);
            this.create(user);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
            simpleResponse.setReturnMsg(messageSource.getMessage(Messages.ERROR_EXCEPTION_UNEXPECTED, null, ContextHelper.getLocale()));
        }
        simpleResponse.setReturnCode(Utils.ReturnCode.SUCCESS.getValue());
        simpleResponse.setReturnMsg(messageSource.getMessage(Messages.INFO_SUCCESS, null, ContextHelper.getLocale()));
        return simpleResponse;
    }

    private boolean checkUserExisted(String userName) {
        User byUserName = userRepository.findByUserName(userName);
        if (byUserName != null) {
            return true;
        }
        return false;
    }

    @Override
    public SimpleResponse updateUser(ReqCreateUser reqCreateUser) {
        SimpleResponse simpleResponse = new SimpleResponse();
        try {
            String userName = reqCreateUser.getUserName();
            if (!checkUserExisted(userName)) {
                createNewUser(reqCreateUser);
            } else {
                User byUserName = userRepository.findByUserName(userName);
                byUserName.setUuidDevice(reqCreateUser.getUuidDevice());
                byUserName.setBirthday(reqCreateUser.getBirthday());
                byUserName.setFullName(reqCreateUser.getFullName());
                byUserName.setEmail(reqCreateUser.getEmail());
                byUserName.setPhoneNumber(reqCreateUser.getPhoneNumber());
                byUserName.setStatus(Utils.UserStatus.ACTIVE.getValue());
                update(byUserName.getId(), byUserName);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
            simpleResponse.setReturnMsg(messageSource.getMessage(Messages.ERROR_EXCEPTION_UNEXPECTED, null, ContextHelper.getLocale()));
        }
        simpleResponse.setReturnCode(Utils.ReturnCode.SUCCESS.getValue());
        simpleResponse.setReturnMsg(messageSource.getMessage(Messages.INFO_SUCCESS, null, ContextHelper.getLocale()));
        return simpleResponse;
    }

    @Override
    public SimpleResponse changePass(ReqChangePassword reqChangePassword) {
        SimpleResponse simpleResponse = new SimpleResponse();
        try {
            if (!checkUserExisted(reqChangePassword.getUserName())) {
                simpleResponse.setReturnMsg(messageSource.getMessage(Messages.WARN_USER_NOT_EXIST, null, ContextHelper.getLocale()));
                simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
                return simpleResponse;
            }
            User user = userRepository.findByUserName(reqChangePassword.getUserName());
            if (PasswordUtils.validatePassword(reqChangePassword.getOldPassword(), user.getPassword())) {
                String newPassword = reqChangePassword.getNewPassword();
                if (PasswordUtils.validatePassword(newPassword, user.getPassword())) {
                    simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
                    simpleResponse.setReturnMsg(messageSource.getMessage(Messages.WARN_NEW_PSWD_MATCH_OLD_PSWD, null, ContextHelper.getLocale()));
                } else {
                    user.setPassword(PasswordUtils.generatePasswordHash(newPassword));
                    update(user.getId(), user);
                    simpleResponse.setReturnMsg(messageSource.getMessage(Messages.INFO_CHANGE_PSWD, null, ContextHelper.getLocale()));
                    simpleResponse.setReturnCode(Utils.ReturnCode.SUCCESS.getValue());
                }
            }else {
                simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
                simpleResponse.setReturnMsg(messageSource.getMessage(Messages.WARN_OLD_PSWD_NOT_MATCH, null, ContextHelper.getLocale()));
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            simpleResponse.setReturnCode(Utils.ReturnCode.FAIL.getValue());
            simpleResponse.setReturnMsg(messageSource.getMessage(Messages.ERROR_EXCEPTION_UNEXPECTED, null, ContextHelper.getLocale()));
        }
        return simpleResponse;
    }
}
