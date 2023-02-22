package com.example.baseremotecontroller.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.model.entity.widget.BaseEntity;
import com.example.baseremotecontroller.model.storage.DataStorage;
import com.example.baseremotecontroller.utility.Constants;
import com.example.baseremotecontroller.utility.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class MenuRepositoryImpl implements MenuRepository {
    private static final String TAG = MenuRepositoryImpl.class.getSimpleName();
    private static MenuRepositoryImpl instance;

    private final DataStorage mDataStorage;
    private final MediatorLiveData<Long> mCurrentMenuId;

    private MenuRepositoryImpl(Application application) {
        mDataStorage = DataStorage.getInstance(application);
        mCurrentMenuId = new MediatorLiveData<>();
    }

    public static MenuRepositoryImpl getInstance(Application application) {
        if (instance == null) {
            instance = new MenuRepositoryImpl(application);
        }

        return instance;
    }

    @Override
    public void chooseMenu(long menuId) {
        if (mCurrentMenuId.getValue() == null || mCurrentMenuId.getValue() != menuId) {
            mCurrentMenuId.postValue(menuId);
        }
    }

    @Override
    public void createMenu(long parentId, String menuName) {
        MenuEntity menu = new MenuEntity();
        menu.creationTime = System.currentTimeMillis();
        menu.parentId = parentId;
        if (menuName != null) {
            menu.name = menuName;
        }

        mDataStorage.addMenu(menu);
    }

    @Override
    public void removeMenu(long menuId) {
        mDataStorage.deleteMenu(menuId);
    }

    @Override
    public void updateMenu(MenuEntity menu) {
        mDataStorage.updateMenu(menu);
    }

    @Override
    public void removeMenuFromPage(long pageId) {
        mDataStorage.deleteMenusFromPage(pageId);
    }

    @Override
    public LiveData<List<MenuEntity>> getAllMenus() {
        return mDataStorage.getAllMenus();
    }

    @Override
    public List<MenuEntity> getCurrentPageMenus(long pageId) {
        return mDataStorage.getCurrentPageMenus(pageId);
    }

    @Override
    public LiveData<Long> getCurrentMenuId() {
        return mCurrentMenuId;
    }

    @Override
    public LiveData<MenuEntity> getMenu(long id) {
        return mDataStorage.getMenu(id);
    }

    @Override
    public LiveData<MenuEntity> getCurrentMenu() {
        return Transformations.switchMap(mCurrentMenuId,
                (Function<Long, LiveData<MenuEntity>>) id -> mDataStorage.getMenu(id));
    }

    @Override
    public void addWidget(Long parentId, WidgetEntity widget) {
        Log.i(TAG, "Add widget: " + widget.name);
        if (parentId != null) {
            widget.parentId = parentId;
            mDataStorage.addWidget(widget);
        }
    }

    @Override
    public void createWidget(Long parentId, String widgetType) {
        WidgetEntity widget = getWidgetFromType(parentId, widgetType);
        if (widget == null) return;

        mDataStorage.addWidget(widget);

        Log.i(TAG, "Widget added to database: " + widgetType);
    }

    @Override
    public void deleteWidget(WidgetEntity widget) {
        mDataStorage.deleteWidget(widget);
    }

    @Override
    public void deleteWidgetsFromMenu(long menuId) {
        mDataStorage.deleteWidgetsFromMenu(menuId);
    }

    @Override
    public void updateWidget(WidgetEntity widget) {
        mDataStorage.updateWidget(widget);
    }

    @Override
    public LiveData<WidgetEntity> findWidget(long widgetId) {
        return mDataStorage.getWidget(mCurrentMenuId.getValue(), widgetId);
    }

    @Override
    public List<WidgetEntity> getWidgets(long menuId) {
        return mDataStorage.getWidgetList(menuId);
    }

    private WidgetEntity getWidgetFromType(long menuId, String widgetType) {
        String classPath = String.format(Constants.ENTITY_FORMAT, widgetType.toLowerCase(), widgetType);
        Object object = Utils.getObjectFromClassName(classPath);

        if (!(object instanceof BaseEntity)) {
            Log.e(TAG, "Widget can not be created from: " + classPath);
            return null;
        }

        BaseEntity widgetData = (BaseEntity) object;

        String widgetName = "";
        for (int count = 1; count > 0; count++) {
            widgetName = String.format(Locale.getDefault(), Constants.WIDGET_NAMING, widgetType, count);

            if (!mDataStorage.widgetNameExists(menuId, widgetName)) {
                break;
            }
        }

        WidgetEntity widget = new WidgetEntity();
        widget.name = widgetName;
        widget.parentId = menuId;
        widget.type = widgetType;
        widget.data = new Gson().toJson(widgetData);

        return widget;
    }
}
