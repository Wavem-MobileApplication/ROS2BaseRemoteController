package com.example.baseremotecontroller.ui.fragment.setting.widget;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentSettingWidgetBinding;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

public class SettingWidgetFragment extends Fragment {
    private FragmentSettingWidgetBinding binding;
    private ContentsViewModel contentsViewModel;
    private SettingWidgetAdapter adapter;
    private NavController navController;

    public static SettingWidgetFragment newInstance(int num) {
        SettingWidgetFragment fragment = new SettingWidgetFragment();
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
        binding = FragmentSettingWidgetBinding.inflate(inflater, container, false);

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
        adapter = new SettingWidgetAdapter();
        adapter.setOnItemClickListener(widget -> {
            contentsViewModel.editWidget(widget);
            navController.navigate(R.id.action_settingWidgetFragment_to_detailWidgetFragment);
        });

        binding.rvWidgetSetting.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWidgetSetting.setHasFixedSize(true);
        binding.rvWidgetSetting.setAdapter(adapter);
    }

    private void initObservers() {
        contentsViewModel.getEditedMenuWidgetList().observe(requireActivity(), widgetEntities -> {
            adapter.setWidgetList(widgetEntities);
            Log.d("위젯생성됨", "Ok");
        });
    }

    private void initClickEvents() {
        binding.btnAddNewWidget.setOnClickListener(view ->
                showWidgetCreateDialog());
    }

    private void showWidgetCreateDialog() {
        String[] widgetNames = getResources().getStringArray(R.array.widget_names);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle("새 위젯 추가");

        dialogBuilder.setItems(widgetNames, (dialogInterface, i) -> {
            String widgetType = widgetNames[i];
            contentsViewModel.createWidget(widgetType);
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private final OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            navController.popBackStack();
        }
    };
}