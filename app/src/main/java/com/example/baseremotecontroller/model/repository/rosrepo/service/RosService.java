package com.example.baseremotecontroller.model.repository.rosrepo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.executors.Executor;
import org.ros2.rcljava.executors.SingleThreadedExecutor;

import java.util.Timer;
import java.util.TimerTask;

public class RosService extends Service {

    private static final String TAG = RosService.class.getSimpleName();
    private static final long SPINNER_DELAY = 0;
    private static final long SPINNER_PERIOD_MS = 200;

    private IBinder mBinder;
    private Executor rosExecutor;
    private Timer timer;
    private Handler handler;
    private WifiManager.MulticastLock lock;

    // Constructor ---------------------------------------------------------------------------------
    public class LocalBinder extends Binder {
        public RosService getService() {
            return RosService.this;
        }
    }

    public RosService() {
        mBinder = new LocalBinder();
        rosExecutor = createExecutor();
        timer = new Timer();
        handler = new Handler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        RCLJava.rclJavaInit();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(getRosSpinRunnable());
            }
        }, SPINNER_DELAY, SPINNER_PERIOD_MS);

        settingWifiManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (timer != null) {
            timer.cancel();
        }

        if (lock != null && lock.isHeld()) {
            lock.release();
        }

        if (RCLJava.ok()) {
            RCLJava.shutdown();
        }
    }

    // ROS2 ----------------------------------------------------------------------------------------
    public void run() {
        rosExecutor.spinSome();
    }

    public Runnable getRosSpinRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                rosExecutor.spinSome();
            }
        };

        return runnable;
    }

    public Executor getExecutor() {
        return rosExecutor;
    }

    protected Executor createExecutor() {
        Log.d("TAG", "createRosExecutor");
        return new SingleThreadedExecutor();
    }

    //WiFi Manager ---------------------------------------------------------------------------------
    private void settingWifiManager() {
        final WifiManager wifi =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("ssdp");
        lock.acquire();
    }
}
