package com.example.baseremotecontroller.ui.view.detail;

import android.view.View;

import com.example.baseremotecontroller.model.entity.WidgetEntity;

public abstract class BaseViewHolder {
    public abstract void initView(View view);

    public abstract void bindEntity(WidgetEntity entity);

    public abstract void updateEntity();

    public abstract WidgetEntity getEntity();
}
