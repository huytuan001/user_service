package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class ResUpdateNewUser {
    @JsonProperty("returnCode")
    private Integer returnCode;

    @JsonProperty("returnMsg")
    private String returnMsg;

    @JsonProperty("data")
    private ResUserData data;

    public ResUpdateNewUser() {
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public ResUserData getData() {
        return data;
    }

    public void setData(ResUserData data) {
        this.data = data;
    }
}
