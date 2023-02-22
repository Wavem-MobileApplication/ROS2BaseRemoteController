package com.example.baseremotecontroller.ui.fragment.rviz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentRvizBinding;


public class RvizFragment extends Fragment {
    private FragmentRvizBinding binding;

    public static RvizFragment newInstance(int num) {
        RvizFragment fragment = new RvizFragment();
        Bundle args = new Bundle();
        args.getInt("number", num);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int num = getArguments().getInt("number");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRvizBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}