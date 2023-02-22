package com.example.baseremotecontroller.ui.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private ViewPager2 viewPager;
    private FragmentStateAdapter viewPagerAdapter;

    public static HomeFragment newInstance(int num) {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        viewPager = requireActivity().findViewById(R.id.viewpager_main);
        viewPagerAdapter = (FragmentStateAdapter) viewPager.getAdapter();
    }
}