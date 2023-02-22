package com.example.baseremotecontroller.ui.general;

public class Position {
    public String widgetName;
    public int posX, posY, width, height;

    public Position() {}

    public Position(String widgetName, int posX, int posY, int width, int height) {
        this.widgetName = widgetName;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
}
