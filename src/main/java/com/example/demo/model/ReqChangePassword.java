package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReqChangePassword {
    @NotNull(message = "{Error.Username.Empty}")
    @NotEmpty(message = "{Error.Username.Empty}")
    private String userName;

    @JsonProperty("oldPassword")
    @NotNull(message = "{Error.Password.Empty}")
    @NotEmpty(message = "{Error.Password.Empty}")
    private String oldPassword;

    @JsonProperty("newPassword")
    @NotNull(message = "{Error.Password.Empty}")
    @NotEmpty(message = "{Error.Password.Empty}")
    private String newPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
