package com.shailesh.chat.models;

public class SignalMessage {
    String type;
    String sender;
    String receiver;
    Object data;

    public SignalMessage() {
    }

    public SignalMessage(String type, String sender, String receiver, Object data) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.data = data;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
