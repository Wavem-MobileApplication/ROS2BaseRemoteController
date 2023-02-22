package com.example.baseremotecontroller.ui.dialog;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentMenuAddDialogBinding;
import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.utility.ToastMessageUtil;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import java.util.List;


public class MenuAddDialogFragment extends DialogFragment {
    private FragmentMenuAddDialogBinding binding;
    private ContentsViewModel contentsViewModel;
    private long editedPageId;

    public static MenuAddDialogFragment newInstance(int num) {
        MenuAddDialogFragment fragment = new MenuAddDialogFragment();
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
        binding = FragmentMenuAddDialogBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        contentsViewModel = new ViewModelProvider(requireActivity()).get(ContentsViewModel.class);

        initObservers();
        initClickEvents();
    }

    private void initObservers() {
        contentsViewModel.getEditedPage().observe(requireActivity(), entity ->
                editedPageId = entity.id);
    }

    private void initClickEvents() {
        binding.btnOkCreateMenu.setOnClickListener(view -> {
            String menuName = binding.etMenuNameCreate.getText().toString();
            if (!menuName.isEmpty()) {
                try {
                    contentsViewModel.addMenu(editedPageId, menuName);
                    dismiss();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                ToastMessageUtil.showToast(getContext(), "생성 할 메뉴 이름을 입력해주세요.");
            }
        });

        binding.btnCancelCreateMenu.setOnClickListener(view -> dismiss());
    }
}