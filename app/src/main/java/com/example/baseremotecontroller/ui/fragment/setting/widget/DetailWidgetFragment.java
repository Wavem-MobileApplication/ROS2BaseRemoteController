package com.example.baseremotecontroller.ui.fragment.setting.widget;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baseremotecontroller.BuildConfig;
import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.databinding.FragmentDetailWidgetBinding;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.ui.view.detail.BaseViewHolder;
import com.example.baseremotecontroller.utility.Constants;
import com.example.baseremotecontroller.utility.Utils;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import java.lang.reflect.Constructor;

public class DetailWidgetFragment extends Fragment {
    private FragmentDetailWidgetBinding binding;
    private ContentsViewModel contentsViewModel;
    private BaseViewHolder widgetHolder;
    private NavController navController;

    public static DetailWidgetFragment newInstance(int num) {
        DetailWidgetFragment fragment = new DetailWidgetFragment();
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
        binding = FragmentDetailWidgetBinding.inflate(inflater, container, false);

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
        contentsViewModel.getEditedWidget().observe(requireActivity(), widget -> {
            if (widget != null) {
                initView(widget);
                Log.d("이닛뷰", widget.type);
            } else {
                Log.d("이닛뷰", "에러");
            }
        });

        initClickEvents();
        requireActivity().getOnBackPressedDispatcher().addCallback(backPressedCallback);
    }

    private void initClickEvents() {
        binding.btnSaveWidget.setOnClickListener(view ->
                contentsViewModel.updateWidget(widgetHolder.getEntity()));
    }

    private void initView(WidgetEntity widget) {
        try {
            String layoutStr = String.format(Constants.DETAIL_LAYOUT_FORMAT, widget.type.toLowerCase());
            int detailContentsLayout = Utils.getResId(layoutStr, R.layout.class);
            LayoutInflater inflater = LayoutInflater.from(binding.containerWidgetDetail.getContext());
            View view = inflater.inflate(detailContentsLayout, binding.containerWidgetDetail, false);

            String viewHolderClassPath = BuildConfig.APPLICATION_ID +
                    String.format(Constants.VIEWHOLDER_FORMAT, widget.type.toLowerCase(), widget.type);
            Class<BaseViewHolder> viewHolderClass = (Class<BaseViewHolder>) Class.forName(viewHolderClassPath);
            Constructor<BaseViewHolder> cons = viewHolderClass.getConstructor();

            widgetHolder = cons.newInstance();
            widgetHolder.initView(view);
            widgetHolder.bindEntity(widget);

            binding.containerWidgetDetail.removeAllViews();
            binding.containerWidgetDetail.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            navController.popBackStack();
        }
    };
}