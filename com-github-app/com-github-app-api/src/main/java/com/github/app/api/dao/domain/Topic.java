package com.github.app.api.dao.domain;

public class Topic {
    private Integer number;

    private String name;

    private Integer popedom;

    private String remark;

    private Integer deviceNumber;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPopedom() {
        return popedom;
    }

    public void setPopedom(Integer popedom) {
        this.popedom = popedom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }
}