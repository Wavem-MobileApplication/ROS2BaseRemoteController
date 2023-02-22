package com.example.baseremotecontroller.ui.view.detail;

import android.view.View;

import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.model.entity.widget.BaseEntity;
import com.example.baseremotecontroller.ui.general.Position;
import com.example.baseremotecontroller.ui.general.RosMessage;
import com.example.baseremotecontroller.utility.Utils;
import com.google.gson.Gson;

public class RosWidgetViewHolder extends BaseViewHolder{

    private PositionViewHolder positionViewHolder;
    private MessageViewHolder messageViewHolder;
    private WidgetEntity widgetEntity;
    private BaseEntity baseEntity;

    public RosWidgetViewHolder() {
        positionViewHolder = new PositionViewHolder(this);
        messageViewHolder =  new MessageViewHolder(this);
    }

    @Override
    public void initView(View view) {
        positionViewHolder.initView(view);
        messageViewHolder.initView(view);
    }

    @Override
    public void bindEntity(WidgetEntity entity) {
        this.widgetEntity = entity;
        this.baseEntity = new Gson().fromJson(entity.data, Utils.getBaseEntityFromWidgetType(entity.type));
        positionViewHolder.bindPosition(new Position(
                entity.name, baseEntity.posX, baseEntity.posY, baseEntity.width, baseEntity.height));
        messageViewHolder.bindMessage(new RosMessage(baseEntity.messageName, baseEntity.messageType));
    }

    @Override
    public void updateEntity() {
        Position posEntity = positionViewHolder.getPosition();
        RosMessage msgEntity = messageViewHolder.getRosMessage();

        widgetEntity.name = posEntity.widgetName;
        baseEntity.posX = posEntity.posX;
        baseEntity.posY = posEntity.posY;
        baseEntity.width = posEntity.width;
        baseEntity.height = posEntity.height;
        baseEntity.messageName = msgEntity.messageName;
        baseEntity.messageType = msgEntity.messageType;

        widgetEntity.data = new Gson().toJson(baseEntity);
    }

    @Override
    public WidgetEntity getEntity() {
        updateEntity();
        return widgetEntity;
    }
}
