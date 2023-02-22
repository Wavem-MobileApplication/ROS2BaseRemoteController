package com.example.baseremotecontroller.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.ActivityMainBinding;
import com.example.baseremotecontroller.model.repository.rosrepo.node.PubNode;
import com.example.baseremotecontroller.model.repository.rosrepo.node.SubNode;
import com.example.baseremotecontroller.model.repository.rosrepo.service.RosService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private RosService rosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        connectToRosService();
    }

    // Service Binding -----------------------------------------------------------------------------
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RosService.LocalBinder lb = (RosService.LocalBinder) service;
            rosService = lb.getService();

            Disposable backgroundTask = Observable.fromCallable(() -> {
                executeRosNodes();
                return true;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> {

                    });
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    private void connectToRosService() {
        Intent intent = new Intent(getApplicationContext(), RosService.class);
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void executeRosNodes() {
        rosService.getExecutor().addNode(PubNode.getInstance());
        rosService.getExecutor().addNode(SubNode.getInstance());
    }
}
