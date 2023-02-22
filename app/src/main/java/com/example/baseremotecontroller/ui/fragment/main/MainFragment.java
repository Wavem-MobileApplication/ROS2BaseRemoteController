package com.example.baseremotecontroller.ui.fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentMainBinding;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.utility.Constants;
import com.example.baseremotecontroller.utility.Utils;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;

    private ContentsViewModel contentsViewModel;

    public static MainFragment newInstance(int num) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("number", num);
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
        binding = FragmentMainBinding.inflate(inflater, container, false);

        init();

        binding.tabMain.setTabGravity(TabLayout.GRAVITY_CENTER);

        return binding.getRoot();
    }

    private void init() {
        contentsViewModel = new ViewModelProvider(requireActivity()).get(ContentsViewModel.class);
        binding.viewpagerMain.setAdapter(new MainViewPagerAdapter(this));
        binding.viewpagerMain.setUserInputEnabled(false);
        new TabLayoutMediator(binding.tabMain, binding.viewpagerMain, true, false,
                (tab, position) -> {
                    tab.setText(Constants.getMenuName(position));
                    Log.d("메디에이터", "DO");
                }).attach();

        initObservers();
        initClickEvents();
        setUpTabLayoutMargin();
    }

    private void initClickEvents() {
        binding.btnNavigation.setOnClickListener(v -> binding.drawerLayoutMain.openDrawer(GravityCompat.START));
    }

    private void setUpTabLayoutMargin() {
        for (int i = 0; i < binding.tabMain.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.tabMain.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMarginEnd((int) Utils.dpToPx(getContext(), 10));
            tab.requestLayout();
        }

        binding.tabMain.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initObservers() {
        contentsViewModel.getAllPages().observe(requireActivity(), pageEntities -> {
            if (pageEntities == null || pageEntities.isEmpty()) {
                contentsViewModel.addPage("기본 페이지");
            }
        });
    }
}