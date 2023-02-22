package com.example.baseremotecontroller.model.repository;

import androidx.lifecycle.LiveData;

import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.WidgetEntity;

import java.util.List;

public interface MenuRepository {

    void chooseMenu(long menuId);

    void createMenu(long parentId, String menuName);

    void removeMenu(long menuId);

    void updateMenu(MenuEntity menu);

    void removeMenuFromPage(long pageId);

    LiveData<List<MenuEntity>> getAllMenus();

    List<MenuEntity> getCurrentPageMenus(long pageId);

    LiveData<Long> getCurrentMenuId();

    LiveData<MenuEntity> getMenu(long id);

    LiveData<MenuEntity> getCurrentMenu();

    void addWidget(Long parentId, WidgetEntity widget);

    void createWidget(Long parentId, String widgetType);

    void deleteWidget(WidgetEntity widget);

    void deleteWidgetsFromMenu(long menuId);

    void updateWidget(WidgetEntity widget);

    LiveData<WidgetEntity> findWidget(long widgetId);

    List<WidgetEntity> getWidgets(long id);
}
