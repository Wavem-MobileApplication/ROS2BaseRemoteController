package com.example.baseremotecontroller.ui.view.detail;

import android.view.View;

import com.example.baseremotecontroller.databinding.DetailMessageBinding;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.model.entity.widget.BaseEntity;
import com.example.baseremotecontroller.ui.general.RosMessage;
import com.example.baseremotecontroller.utility.Utils;
import com.google.gson.Gson;

public class MessageViewHolder {
    private DetailMessageBinding binding;
    private BaseViewHolder parentViewHolder;
    private RosMessage message;

    public MessageViewHolder(BaseViewHolder parentViewHolder) {
        this.parentViewHolder = parentViewHolder;
    }

    public void initView(View view) {
        binding = DetailMessageBinding.bind(view);
    }

    public void bindMessage(RosMessage message) {
        this.message = message;
        updateUiContents();
    }

    public RosMessage getRosMessage() {
        try {
            message.messageName = binding.etMessageName.getText().toString();
            message.messageType = binding.etMessageType.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private void updateUiContents() {
        binding.etMessageName.setText(message.messageName);
        binding.etMessageType.setText(message.messageType);
    }
}
