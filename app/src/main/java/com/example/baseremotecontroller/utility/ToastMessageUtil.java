package com.example.baseremotecontroller.utility;

import android.content.Context;
import android.widget.Toast;

public class ToastMessageUtil {
    private static Toast toast;
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
