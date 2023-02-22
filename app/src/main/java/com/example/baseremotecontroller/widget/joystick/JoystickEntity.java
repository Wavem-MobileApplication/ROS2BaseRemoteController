package com.example.baseremotecontroller.widget.joystick;

import com.example.baseremotecontroller.model.entity.widget.BaseEntity;

public class JoystickEntity extends BaseEntity {

    public int mode;
    public float linearMaxVel;
    public float angularMaxVel;

    public JoystickEntity() {
        this.posX = 0;
        this.posY = 0;
        this.width = 3;
        this.height = 3;
        this.messageName = "cmd_vel";
        this.messageType = "Twist";
        this.mode = 0;
        this.linearMaxVel = 1.00f;
        this.angularMaxVel = 1.00f;
    }
}
