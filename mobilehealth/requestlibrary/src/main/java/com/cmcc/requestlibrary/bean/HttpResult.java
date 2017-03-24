package com.cmcc.requestlibrary.bean;

/**
 * @author shuyang on 2016/10/20.
 */
public class HttpResult<T> {
    private String equipmentData;
    private int code;
    private String message;
    private T data;

    public String getEquipmentData() {
        return equipmentData;
    }

    public void setEquipmentData(String equipmentData) {
        this.equipmentData = equipmentData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
