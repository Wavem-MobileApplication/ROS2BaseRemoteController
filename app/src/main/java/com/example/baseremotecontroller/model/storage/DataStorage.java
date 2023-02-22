package com.example.baseremotecontroller.model.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.baseremotecontroller.model.db.MenuDao;
import com.example.baseremotecontroller.model.db.PageDao;
import com.example.baseremotecontroller.model.db.WidgetDao;
import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.utility.Constants;
import com.example.baseremotecontroller.utility.LambdaTask;

import java.util.List;

@Database(entities = {PageEntity.class, MenuEntity.class, WidgetEntity.class}, version = 1, exportSchema = false)
public abstract class DataStorage extends RoomDatabase {

    private static DataStorage instance;

    public static synchronized DataStorage getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DataStorage.class, Constants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    // Dao Methods ---------------------------------------------------------------------------------
    public abstract PageDao pageDao();
    public abstract MenuDao menuDao();
    public abstract WidgetDao widgetDao();

    // Page Methods ------------------------------------------------------------------------------
    public void addPage(PageEntity page) {
        new LambdaTask(() -> pageDao().insert(page)).dispose();
    }

    public void updatePage(PageEntity page) {
        new LambdaTask(() -> pageDao().update(page)).dispose();
    }

    public void deletePage(PageEntity page) {
        new LambdaTask(() -> pageDao().delete(page)).dispose();
    }

    public void deletePage(long id) {
        new LambdaTask(() -> pageDao().removePage(id)).dispose();
    }

    public LiveData<PageEntity> getPage(long id) {
        return pageDao().getPage(id);
    }

    public LiveData<List<PageEntity>> getAllPages() {
        return pageDao().getAllPages();
    }

    // Menu Method ---------------------------------------------------------------------------------
    public void addMenu(MenuEntity menu) {
        new LambdaTask(() -> menuDao().insert(menu)).dispose();
    }

    public void updateMenu(MenuEntity menu) {
        new LambdaTask(() -> menuDao().update(menu)).dispose();
    }

    public void deleteMenu(MenuEntity menu) {
        new LambdaTask(() -> menuDao().delete(menu)).dispose();
    }

    public void deleteMenu(long id) {
        new LambdaTask(() -> menuDao().removeMenu(id)).dispose();
    }

    public void deleteMenusFromPage(long pageId) {
        new LambdaTask(() -> menuDao().removeMenusFromPage(pageId)).dispose();
    }

    public LiveData<MenuEntity> getMenu(long id) {
        return menuDao().getMenu(id);
    }

    public LiveData<List<MenuEntity>> getAllMenus() {
        return menuDao().getAllMenus();
    }

    public List<MenuEntity> getCurrentPageMenus(long pageId) {
        return menuDao().getMenusFromPage(pageId);
    }

    // Widget Method -------------------------------------------------------------------------------
    public void addWidget(WidgetEntity widget) {
        new LambdaTask(() -> widgetDao().insert(widget)).dispose();
    }

    public void updateWidget(WidgetEntity widget) {
        new LambdaTask(() -> widgetDao().update(widget)).dispose();
    }

    public void deleteWidget(WidgetEntity widget) {
        new LambdaTask(() -> widgetDao().delete(widget)).dispose();
    }

    public void deleteWidgetsFromMenu(long menuId) {
        new LambdaTask(() -> widgetDao().deleteFromMenu(menuId)).dispose();
    }

    public LiveData<WidgetEntity> getWidget(long menuId, long widgetId) {
        return widgetDao().getWidget(menuId, widgetId);
    }

    public List<WidgetEntity> getWidgetList(long menuId) {
        return widgetDao().getWidgetsFromMenu(menuId);
    }

    public boolean widgetNameExists(long menuId, String name) {
        return widgetDao().exists(menuId, name);
    }
}
