package com.example.baseremotecontroller.model.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.baseremotecontroller.model.entity.WidgetEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class WidgetDao implements BaseDao<WidgetEntity> {

    @Query("SELECT * FROM widget_table WHERE parent_id = :parentId")
    protected abstract List<WidgetEntity> getWidgetsFor(long parentId);

    @Query("DELETE FROM widget_table WHERE id = :id")
    abstract void deleteByWidgetId(long id);

    @Query("DELETE FROM widget_table WHERE parent_id = :parentId")
    abstract int deleteWithMenu(long parentId);

    @Query("DELETE FROM widget_table")
    abstract void deleteAll();

    @Query("SELECT EXISTS (SELECT 1 FROM widget_table WHERE parent_id = :parentId AND widget_name = :name)")
    public abstract boolean exists(long parentId, String name);

    @Query("SELECT * FROM widget_table WHERE parent_id = :parentId AND id = :widgetId")
    abstract LiveData<WidgetEntity> getWidgetIntern(long parentId, long widgetId);

    public LiveData<WidgetEntity> getWidget(long parentId, long widgetId) {
        MediatorLiveData<WidgetEntity> widget = new MediatorLiveData<>();

        widget.addSource(getWidgetIntern(parentId, widgetId), new Observer<WidgetEntity>() {
            @Override
            public void onChanged(WidgetEntity widgetEntity) {
                widget.postValue(widgetEntity);
            }
        });

        return widget;
    }

    public List<WidgetEntity> getWidgetsFromMenu(long menuId) {
        List<WidgetEntity> widgetList = new ArrayList<>();
        widgetList.addAll(getWidgetsFor(menuId));

        return widgetList;
    }

    public void delete(WidgetEntity widget) {
        deleteByWidgetId(widget.id);
    }

    public void deleteFromMenu(long menuId) {
        deleteWithMenu(menuId);
    }
}
