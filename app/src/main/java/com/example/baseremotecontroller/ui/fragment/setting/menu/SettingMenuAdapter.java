package com.example.baseremotecontroller.ui.fragment.setting.menu;

import android.service.autofill.TextValueSanitizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.model.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

public class SettingMenuAdapter extends RecyclerView.Adapter<SettingMenuAdapter.ViewHolder> {

    private List<MenuEntity> menuList;
    private OnItemClickListener listener;

    public SettingMenuAdapter() {
        menuList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_setting_menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuEntity menu = menuList.get(position);

        holder.tvMenuName.setText(menu.name);
        holder.cvMenuItem.setOnClickListener(view -> listener.onClick(menu));
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

    public interface OnItemClickListener {
        void onClick(MenuEntity menu);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvMenuItem;
        private TextView tvMenuName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvMenuItem = itemView.findViewById(R.id.cv_setting_menu_item);
            tvMenuName = itemView.findViewById(R.id.tv_setting_menu_name);
        }
    }
}
