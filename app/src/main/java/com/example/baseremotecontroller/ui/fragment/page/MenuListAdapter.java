package com.example.baseremotecontroller.ui.fragment.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private List<MenuEntity> menuList;
    private ContentsViewModel viewModel;
    private LifecycleOwner owner;

    public MenuListAdapter(ContentsViewModel viewModel, LifecycleOwner owner) {
        this.viewModel = viewModel;
        this.owner = owner;
        menuList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuEntity menu = menuList.get(position);

        holder.tvMenuName.setText(menu.name);
        holder.tvMenuName.setOnClickListener(v -> {
            viewModel.selectMenu(menu.id);
            viewModel.selectPage(menu.parentId);
        });

        viewModel.getCurrentMenu().observe(owner, menuEntity -> {
            if (menu.id == menuEntity.id) {
                holder.ivSelectedMenu.setVisibility(View.VISIBLE);
            } else {
                holder.ivSelectedMenu.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setMenuList(List<MenuEntity> menuList) {
        this.menuList.clear();
        this.menuList.addAll(menuList);
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelectedMenu;
        TextView tvMenuName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSelectedMenu = itemView.findViewById(R.id.iv_selected_menu_item);
            tvMenuName = itemView.findViewById(R.id.tv_menu_name_item);
        }
    }
}
