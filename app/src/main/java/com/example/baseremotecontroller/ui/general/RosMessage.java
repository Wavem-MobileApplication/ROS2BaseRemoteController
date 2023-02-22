package com.example.baseremotecontroller.ui.general;

public class RosMessage {
    public String messageName, messageType;

    public RosMessage() {}

    public RosMessage(String messageName, String messageType) {
        this.messageName = messageName;
        this.messageType = messageType;
    }
}
