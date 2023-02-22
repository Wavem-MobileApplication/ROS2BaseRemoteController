package com.example.baseremotecontroller.ui.fragment.page;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseremotecontroller.R;
import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.viewmodel.ContentsViewModel;

import java.util.ArrayList;
import java.util.List;

public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> {
    private List<PageEntity> pageList;
    private ContentsViewModel viewModel;
    private LifecycleOwner owner;
    private Context context;

    private OnItemClickListener listener;

    public PageListAdapter(ContentsViewModel viewModel, LifecycleOwner owner, Context context) {
        pageList = new ArrayList<>();
        this.viewModel = viewModel;
        this.owner = owner;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_page_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PageEntity page = pageList.get(position);
        holder.tvPageName.setText(page.name);

        holder.btnDropMenu.setOnClickListener(v -> {
            if (holder.rvMenu.getVisibility() != View.VISIBLE) {
                holder.rvMenu.setVisibility(View.VISIBLE);
                Log.d("보이기", "true");
            } else {
                holder.rvMenu.setVisibility(View.GONE);
                Log.d("보이기", "false");
            }
        });

        holder.tvPageName.setOnClickListener(v -> {
            viewModel.selectPage(page.id);
        });

        viewModel.getCurrentPage().observe(owner, p -> {
            if (page.id == p.id) {
                holder.ivSelectedPage.setVisibility(View.VISIBLE);
            } else {
                holder.ivSelectedPage.setVisibility(View.INVISIBLE);
            }
        });

        initChildRecyclerView(holder.rvMenu, page.id);
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    private void initChildRecyclerView(RecyclerView rv, long pageId) {
        MenuListAdapter adapter = new MenuListAdapter(viewModel, owner);

        viewModel.getAllMenus().observe(owner, menuEntities -> {
            List<MenuEntity> menus = new ArrayList<>();
            for (MenuEntity menu : menuEntities) {
                if (menu.parentId == pageId) {
                    menus.add(menu);
                }
            }
            adapter.setMenuList(menus);
        });

        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
    }

    public void setPageList(List<PageEntity> pageList) {
        this.pageList.clear();
        this.pageList.addAll(pageList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(PageEntity entity);
    }

    public void setOnItemCLickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivSelectedPage;
        private ImageButton btnDropMenu;
        private TextView tvPageName;
        private RecyclerView rvMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSelectedPage = itemView.findViewById(R.id.iv_selected_page_item);
            btnDropMenu = itemView.findViewById(R.id.btn_drop_menu_item);
            tvPageName = itemView.findViewById(R.id.tv_page_name_item);
            rvMenu = itemView.findViewById(R.id.rv_menu_inner);
        }
    }
}
