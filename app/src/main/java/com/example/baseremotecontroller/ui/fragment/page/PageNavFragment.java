package com.example.baseremotecontroller.ui.fragment.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentPageNavBinding;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.ui.dialog.PageAddDialogFragment;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import java.util.List;

public class PageNavFragment extends Fragment {
    private FragmentPageNavBinding binding;

    private ContentsViewModel contentsViewModel;
    private PageListAdapter adapter;

    public static PageNavFragment newInstance(int num) {
        PageNavFragment fragment = new PageNavFragment();
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
        binding = FragmentPageNavBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        contentsViewModel = new ViewModelProvider(requireActivity()).get(ContentsViewModel.class);
        initRecyclerView();
        initObservers();
        initClickEvents();
    }

    private void initRecyclerView() {
        adapter = new PageListAdapter(contentsViewModel, requireActivity(), getContext());
        binding.rvPageOuter.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPageOuter.setHasFixedSize(true);
        binding.rvPageOuter.setAdapter(adapter);
    }

    private void initObservers() {
        contentsViewModel.getAllPages().observe(requireActivity(), pageEntities -> {
            adapter.setPageList(pageEntities);
            Log.d("페이지사이즈", String.valueOf(pageEntities.size()));
        });
    }

    private void initClickEvents() {
        binding.btnAddPage.setOnClickListener(v -> {
            PageAddDialogFragment dialog = new PageAddDialogFragment();
            dialog.show(requireActivity().getSupportFragmentManager(), "PageAddDialog");
        });
    }
}