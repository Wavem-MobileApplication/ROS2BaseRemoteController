package com.example.baseremotecontroller.ui.fragment.setting.page;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentSettingBinding;
import com.example.baseremotecontroller.databinding.FragmentSettingPageBinding;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.ui.dialog.PageAddDialogFragment;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import org.ros2.rcljava.common.JNIUtils;

import java.util.List;

public class SettingPageFragment extends Fragment {

    private FragmentSettingPageBinding binding;
    private NavController navController;
    private ContentsViewModel contentsViewModel;
    private SettingPageListAdapter adapter;

    public static SettingPageFragment newInstance(int num) {
        SettingPageFragment fragment = new SettingPageFragment();
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
        binding = FragmentSettingPageBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void init() {
        contentsViewModel = new ViewModelProvider(requireActivity()).get(ContentsViewModel.class);
        initRecyclerView();
        initClickEvents();
    }

    private void initRecyclerView() {
        adapter = new SettingPageListAdapter();
        adapter.setOnItemClickListener(entity -> {
            contentsViewModel.editPage(entity);
            navController.navigate(R.id.settingMenuFragment);
        });

        binding.rvPageSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPageSetting.setHasFixedSize(true);
        binding.rvPageSetting.setAdapter(adapter);

        contentsViewModel.getAllPages().observe(requireActivity(), pageEntities ->
                adapter.setPageList(pageEntities));
    }

    private void initClickEvents() {
        binding.btnAddNewPage.setOnClickListener(view -> {
            PageAddDialogFragment dialog = new PageAddDialogFragment();
            dialog.show(requireActivity().getSupportFragmentManager(), "AddPageDialog");
        });
    }
}