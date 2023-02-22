package com.example.baseremotecontroller.ui.dialog;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentPageAddDialogBinding;
import com.example.baseremotecontroller.databinding.FragmentPageNavBinding;
import com.example.baseremotecontroller.utility.ToastMessageUtil;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;


public class PageAddDialogFragment extends DialogFragment {
    private FragmentPageAddDialogBinding binding;

    private ContentsViewModel contentsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageAddDialogBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        contentsViewModel = new ViewModelProvider(requireActivity()).get(ContentsViewModel.class);

        binding.btnOkCreatePage.setOnClickListener(v -> {
            String pageName = binding.etPageNameCreate.getText().toString();
            if (!pageName.isEmpty()) {
                contentsViewModel.addPage(pageName);
                ToastMessageUtil.showToast(getContext(), pageName + "페이지가 생성되었습니다.");
                dismiss();
            } else {
                ToastMessageUtil.showToast(getContext(), "생성 할 페이지 이름을 입력해주세요.");
            }
        });

        binding.btnCancelCreatePage.setOnClickListener(v -> dismiss());
    }
}