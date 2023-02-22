package com.example.baseremotecontroller.ui.fragment.setting.menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentSettingMenuBinding;
import com.example.baseremotecontroller.ui.dialog.MenuAddDialogFragment;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;


public class SettingMenuFragment extends Fragment {
    private FragmentSettingMenuBinding binding;
    private SettingMenuAdapter adapter;
    private ContentsViewModel contentsViewModel;
    private NavController navController;

    public static SettingMenuFragment newInstance(int num) {
        SettingMenuFragment fragment = new SettingMenuFragment();
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
        binding = FragmentSettingMenuBinding.inflate(inflater, container, false);

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
        requireActivity().getOnBackPressedDispatcher().addCallback(backPressedCallback);

        initRecyclerView();
        initObservers();
        initClickEvents();
    }

    private void initRecyclerView() {
        adapter = new SettingMenuAdapter();
        adapter.setOnItemClickListener(menu -> {
            contentsViewModel.editMenu(menu);
            navController.navigate(R.id.action_settingMenuFragment_to_settingWidgetFragment);
        });

        binding.rvMenuSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMenuSetting.setHasFixedSize(true);
        binding.rvMenuSetting.setAdapter(adapter);
    }

    private void initClickEvents() {
        binding.btnAddNewMenu.setOnClickListener(view -> {
            MenuAddDialogFragment dialog = new MenuAddDialogFragment();
            dialog.show(requireActivity().getSupportFragmentManager(), "MenuAddDialog");
        });
    }

    private void initObservers() {
        contentsViewModel.getEditedPageMenuList().observe(requireActivity(), menuEntities -> {
            adapter.setMenuList(menuEntities);
        });
    }

    private final OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            navController.popBackStack();
        }
    };
}