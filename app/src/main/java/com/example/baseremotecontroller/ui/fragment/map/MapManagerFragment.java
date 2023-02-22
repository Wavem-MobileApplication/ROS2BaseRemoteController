package com.example.baseremotecontroller.ui.fragment.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentMapManagerBinding;


public class MapManagerFragment extends Fragment {
    private FragmentMapManagerBinding binding;

    public static MapManagerFragment newInstance(int num) {
        MapManagerFragment fragment = new MapManagerFragment();
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
        binding = FragmentMapManagerBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }


}