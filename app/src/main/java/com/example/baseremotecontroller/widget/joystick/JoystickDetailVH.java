package com.example.baseremotecontroller.widget.joystick;

import android.util.Log;
import android.view.View;

import com.example.baseremotecontroller.databinding.WidgetDetailJoystickBinding;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.ui.view.detail.RosWidgetViewHolder;
import com.google.gson.Gson;

public class JoystickDetailVH extends RosWidgetViewHolder {
    private static final int MODE_DEFAULT = 0;
    private static final int MODE_LINEAR = 1;
    private static final int MODE_ANGULAR = 2;
    private WidgetDetailJoystickBinding binding;
    private WidgetEntity entity;
    private JoystickEntity joystickEntity;

    @Override
    public void initView(View view) {
        super.initView(view);
        binding = WidgetDetailJoystickBinding.bind(view);
    }

    @Override
    public void bindEntity(WidgetEntity entity) {
        super.bindEntity(entity);
        this.entity = entity;
        joystickEntity = new Gson().fromJson(entity.data, JoystickEntity.class);
        binding.etJoystickMaxVelAngular.setText(String.valueOf(joystickEntity.angularMaxVel));
        binding.etJoystickMaxVelLinear.setText(String.valueOf(joystickEntity.linearMaxVel));

        switch (joystickEntity.mode) {
            case MODE_DEFAULT:
                binding.rbDefault.setChecked(true);
                Log.d("Mode", "Default");
                break;
            case MODE_LINEAR:
                binding.rbLinear.setChecked(true);
                Log.d("Mode", "Linear");
                break;
            case MODE_ANGULAR:
                binding.rbAngular.setChecked(true);
                Log.d("Mode", "Angular");
                break;
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        joystickEntity.linearMaxVel = Float.parseFloat(binding.etJoystickMaxVelLinear.getText().toString());
        joystickEntity.angularMaxVel = Float.parseFloat(binding.etJoystickMaxVelAngular.getText().toString());

        if (binding.rbDefault.isChecked()) {
            joystickEntity.mode = MODE_DEFAULT;
            Log.d("Set Mode", "Default");
        } else if (binding.rbLinear.isChecked()) {
            joystickEntity.mode = MODE_LINEAR;
            Log.d("Set Mode", "Linear");
        } else if (binding.rbAngular.isChecked()) {
            joystickEntity.mode = MODE_ANGULAR;
            Log.d("Set Mode", "Angular");
        }
    }

    @Override
    public WidgetEntity getEntity() {
        Gson gson = new Gson();
        entity = super.getEntity();
        updateEntity();

        JoystickEntity dataEntity = gson.fromJson(entity.data, JoystickEntity.class);
        dataEntity.linearMaxVel = joystickEntity.linearMaxVel;
        dataEntity.angularMaxVel = joystickEntity.angularMaxVel;
        dataEntity.mode = joystickEntity.mode;
        entity.data = gson.toJson(dataEntity);
        
        return entity;
    }
}
