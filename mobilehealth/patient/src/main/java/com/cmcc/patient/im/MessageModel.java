package com.cmcc.patient.im;

public class MessageModel {
    private int msgId;
    private String msgText;
    private String msgThumbPath;
    private String msgOriginalPath;
    private String msgOriginalUri;
    private String msgByUser;
    private int msgUiType;
    private String msgStatus;
    private int progress;

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgThumbPath() {
        return msgThumbPath;
    }

    public void setMsgThumbPath(String msgThumbPath) {
        this.msgThumbPath = msgThumbPath;
    }


    public String getMsgOriginalPath() {
        return msgOriginalPath;
    }

    public void setMsgOriginalPath(String msgOriginalPath) {
        this.msgOriginalPath = msgOriginalPath;
    }


    public String getMsgOriginalUri() {
        return msgOriginalUri;
    }

    public void setMsgOriginalUri(String msgOriginalUri) {
        this.msgOriginalUri = msgOriginalUri;
    }

    public String getMsgByUser() {
        return msgByUser;
    }

    public void setMsgByUser(String msgByUser) {
        this.msgByUser = msgByUser;
    }

    public int getMsgUiType() {
        return msgUiType;
    }

    public void setMsgUiType(int msgUiType) {
        this.msgUiType = msgUiType;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}
