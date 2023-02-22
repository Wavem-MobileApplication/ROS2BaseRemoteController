package com.example.baseremotecontroller.ui.fragment.setting.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.model.entity.WidgetEntity;

import java.util.ArrayList;
import java.util.List;

public class SettingWidgetAdapter extends RecyclerView.Adapter<SettingWidgetAdapter.ViewHolder> {

    private List<WidgetEntity> widgetList;
    private OnItemClickListener listener;

    public SettingWidgetAdapter() {
        widgetList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting_widget_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WidgetEntity widget = widgetList.get(position);

        holder.tvWidgetName.setText(widget.name);
        holder.cvWidgetItem.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick(widget);
            }
        });
    }

    @Override
    public int getItemCount() {
        return widgetList.size();
    }

    public void setWidgetList(List<WidgetEntity> widgetList) {
        this.widgetList.clear();
        this.widgetList.addAll(widgetList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(WidgetEntity widget);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvWidgetItem;
        private TextView tvWidgetName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvWidgetItem = itemView.findViewById(R.id.cv_setting_widget_item);
            tvWidgetName = itemView.findViewById(R.id.tv_setting_widget_name);
        }
    }
}
