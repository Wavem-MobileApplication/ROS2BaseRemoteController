package com.example.baseremotecontroller.utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.baseremotecontroller.BuildConfig;
import com.example.baseremotecontroller.model.entity.widget.BaseEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Utils {
    public static float pxToCm(Context context, float px) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float cm = px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 10, dm);

        return cm;
    }

    public static float cmToPx(Context context, float cm) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, cm*10, dm);

        return px;
    }

    public static float dpToPx(Context context, float dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);

        return px;
    }

    public static Object getObjectFromClassName(String relativeClassPath) {
        String classPath = BuildConfig.APPLICATION_ID + relativeClassPath;

        try {
            Class<?> clazz = Class.forName(classPath);
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<? extends BaseEntity> getBaseEntityFromWidgetType(String widgetType) {
        String entityClassPath = BuildConfig.APPLICATION_ID +
                String.format(Constants.ENTITY_FORMAT, widgetType.toLowerCase(), widgetType);
        try {
            Class<? extends BaseEntity> entityClass =
                    (Class<? extends BaseEntity>) Class.forName(entityClassPath);
            return entityClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void hideSoftKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getResId(String resName, Class<?> clazz) {
        try {
            Field idField = clazz.getDeclaredField(resName);
            return idField.getInt(idField);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
