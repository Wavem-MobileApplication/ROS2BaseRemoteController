package com.example.baseremotecontroller.ui.fragment.setting.page;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.model.entity.PageEntity;

import java.util.ArrayList;
import java.util.List;

public class SettingPageListAdapter extends RecyclerView.Adapter<SettingPageListAdapter.ViewHolder> {

    private List<PageEntity> pageList;
    private OnItemClickListener listener;

    public SettingPageListAdapter() {
        pageList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_setting_page_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PageEntity page = pageList.get(position);

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(page);
            }
        });
        holder.textView.setText(page.name);
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    public void setPageList(List<PageEntity> pageList) {
        this.pageList.clear();
        this.pageList.addAll(pageList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(PageEntity entity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_setting_page_item);
            textView = itemView.findViewById(R.id.tv_setting_page_name);
        }
    }

}
