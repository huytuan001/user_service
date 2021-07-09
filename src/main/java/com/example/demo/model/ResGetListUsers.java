package com.example.demo.model;

import java.util.List;

public class ResGetListUsers {
    private Integer returnCode;
    private String returnMsg;
    private List<ResUserData> data;
    private PageInfo page;

    public ResGetListUsers() {
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

    public List<ResUserData> getData() {
        return data;
    }

    public void setData(List<ResUserData> data) {
        this.data = data;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }
}
