package com.example.baseremotecontroller.ui.fragment.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baseremotecontroller.ui.fragment.home.HomeFragment;
import com.example.baseremotecontroller.ui.fragment.map.MapManagerFragment;
import com.example.baseremotecontroller.ui.fragment.rviz.RvizFragment;
import com.example.baseremotecontroller.ui.fragment.setting.SettingFragment;
import com.example.baseremotecontroller.utility.Constants;

import java.util.HashMap;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private HashMap<Integer, Fragment> fragments;

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        init();
    }

    public MainViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        init();
    }

    private void init() {
        fragments = new HashMap<>();
        fragments.put(Constants.HOME_FRAGMENT_NUM, HomeFragment.newInstance(Constants.HOME_FRAGMENT_NUM));
        fragments.put(Constants.RVIZ_FRAGMENT_NUM, RvizFragment.newInstance(Constants.RVIZ_FRAGMENT_NUM));
        fragments.put(Constants.MAP_FRAGMENT_NUM, MapManagerFragment.newInstance(Constants.MAP_FRAGMENT_NUM));
        fragments.put(Constants.SETTING_FRAGMENT_NUM, SettingFragment.newInstance(Constants.SETTING_FRAGMENT_NUM));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
