package com.example.baseremotecontroller.ui.view.detail;

import android.view.View;

import com.example.baseremotecontroller.databinding.DetailPositionBinding;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.model.entity.widget.BaseEntity;
import com.example.baseremotecontroller.ui.general.Position;
import com.example.baseremotecontroller.utility.Utils;
import com.google.gson.Gson;

public class PositionViewHolder {
    private DetailPositionBinding binding;
    private BaseViewHolder parentViewHolder;

    private Position position;

    public PositionViewHolder(BaseViewHolder parentViewHolder) {
        this.parentViewHolder = parentViewHolder;
    }

    public void initView(View view) {
        binding = DetailPositionBinding.bind(view);
    }

    public void bindPosition(Position position) {
        this.position = position;
        updateUiContents();
    }

    public Position getPosition() {
        try {
            position.widgetName = binding.etWidgetNameDetail.getText().toString();
            position.posX = Integer.parseInt(binding.etWidgetPositionX.getText().toString());
            position.posY = Integer.parseInt(binding.etWidgetPositionY.getText().toString());
            position.width = Integer.parseInt(binding.etWidgetPositionWidth.getText().toString());
            position.height = Integer.parseInt(binding.etWidgetPositionHeight.getText().toString());

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return position;
    }

    private void updateUiContents() {
        binding.etWidgetNameDetail.setText(position.widgetName);
        binding.etWidgetPositionX.setText(String.valueOf(position.posX));
        binding.etWidgetPositionY.setText(String.valueOf(position.posY));
        binding.etWidgetPositionWidth.setText(String.valueOf(position.width));
        binding.etWidgetPositionHeight.setText(String.valueOf(position.height));
    }
}
