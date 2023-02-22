package com.example.baseremotecontroller.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.baseremotecontroller.model.entity.MenuEntity;
import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.model.entity.WidgetEntity;
import com.example.baseremotecontroller.model.repository.MenuRepository;
import com.example.baseremotecontroller.model.repository.MenuRepositoryImpl;
import com.example.baseremotecontroller.model.repository.PageRepository;
import com.example.baseremotecontroller.model.repository.PageRepositoryImpl;
import com.example.baseremotecontroller.utility.LambdaTask;

import org.ros2.rcljava.RCLJava;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContentsViewModel extends AndroidViewModel {

    private final PageRepository pageRepository;
    private final MenuRepository menuRepository;

    public ContentsViewModel(@NonNull Application application) {
        super(application);

        pageRepository = PageRepositoryImpl.getInstance(application);
        menuRepository = MenuRepositoryImpl.getInstance(application);
        initPageLiveData();
        initMenuLiveData();
        initWidgetLiveData();
    }

    // Page Method -------------------------------------------------------------------------------
    private LiveData<List<PageEntity>> allPages;
    private LiveData<PageEntity> currentPage;
    private MutableLiveData<PageEntity> editedPage;

    private void initPageLiveData() {
        allPages = pageRepository.getAllPages();
        currentPage = pageRepository.getCurrentPage();
        editedPage = new MutableLiveData<>();
    }

    public void renamePage(PageEntity page, String newName) {
        newName = newName.trim();
        page.name = newName;
        pageRepository.updatePage(page);
    }

    public void deletePage(PageEntity page) {
        pageRepository.removePage(page.id);
        deleteMenuFromPage(page.id);
    }

    public void addPage(String name) {
        pageRepository.createPage(name);
    }

    public void selectPage(long pageId) {
        pageRepository.choosePage(pageId);
    }

    public void editPage(PageEntity page) {
        editedPage.postValue(page);
    }

    public LiveData<List<PageEntity>> getAllPages() {
        return allPages;
    }

    public LiveData<PageEntity> getCurrentPage() {
        return currentPage;
    }

    public LiveData<PageEntity> getEditedPage() {
        return editedPage;
    }

    // Menu Methods --------------------------------------------------------------------------------
    private LiveData<List<MenuEntity>> allMenus;
    private MediatorLiveData<List<MenuEntity>> currentMenuList;
    private LiveData<MenuEntity> currentMenu;
    private MediatorLiveData<List<MenuEntity>> editedPageMenuList;
    private MutableLiveData<MenuEntity> editedMenu;

    private void initMenuLiveData() {
        allMenus = menuRepository.getAllMenus();
        currentMenu = menuRepository.getCurrentMenu();
        editedMenu = new MutableLiveData<>();

        currentMenuList = new MediatorLiveData<>();
        currentMenuList.addSource(currentPage, page -> new LambdaTask(() ->
                currentMenuList.postValue(menuRepository.getCurrentPageMenus(page.id))).dispose());

        editedPageMenuList = new MediatorLiveData<>();
        editedPageMenuList.addSource(editedPage, entity -> new LambdaTask(() ->
                editedPageMenuList.postValue(menuRepository.getCurrentPageMenus(entity.id))).dispose());
    }

    public void selectMenu(long menuId) {
        Log.d("메뉴", String.valueOf(menuId));
        menuRepository.chooseMenu(menuId);
    }

    public void renameMenu(MenuEntity menu, String newName) {
        newName = newName.trim();
        menu.name = newName;
        menuRepository.updateMenu(menu);
    }

    public void addMenu(long pageId, String name) {
        Disposable backgroundTask = Observable.fromCallable(() -> {
                    menuRepository.createMenu(pageId, name);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    new LambdaTask(() -> currentMenuList.postValue(
                            menuRepository.getCurrentPageMenus(pageId))).dispose();
                });
    }

    public void deleteMenu(MenuEntity menu) {
        menuRepository.removeMenu(menu.id);
    }

    private void deleteMenuFromPage(long pageId) {
        menuRepository.removeMenuFromPage(pageId);
    }

    public LiveData<List<MenuEntity>> getAllMenus() {
        return allMenus;
    }

    public MediatorLiveData<List<MenuEntity>> getCurrentMenuList() {
        return currentMenuList;
    }

    public LiveData<MenuEntity> getCurrentMenu() {
        return currentMenu;
    }

    public LiveData<List<MenuEntity>> getEditedPageMenuList() {
        return editedPageMenuList;
    }

    public void editMenu(MenuEntity menu) {
        editedMenu.postValue(menu);
    }

    public LiveData<MenuEntity> getEditedMenu() {
        return editedMenu;
    }

    // Widget Methods ------------------------------------------------------------------------------
    private MediatorLiveData<List<WidgetEntity>> currentWidgetList;
    private MediatorLiveData<List<WidgetEntity>> editedMenuWidgetList;
    private MediatorLiveData<WidgetEntity> editedWidget;

    private void initWidgetLiveData() {
        editedWidget = new MediatorLiveData<>();
        currentWidgetList = new MediatorLiveData<>();
        currentWidgetList.addSource(currentMenu, menu ->
                new LambdaTask(() ->
                        currentWidgetList.postValue(menuRepository.getWidgets(menu.id))).dispose());

        editedMenuWidgetList = new MediatorLiveData<>();
        editedMenuWidgetList.addSource(editedMenu, menuEntity -> {
            new LambdaTask(() -> editedMenuWidgetList.postValue(menuRepository.getWidgets(menuEntity.id))).dispose();
        });
    }

    public void addWidget(WidgetEntity widget) {
        menuRepository.addWidget(getEditedMenu().getValue().id, widget);
    }

    public void createWidget(String widgetType) {
        long parentId = getEditedMenu().getValue().id;
        new LambdaTask(() -> menuRepository.createWidget(parentId, widgetType)).dispose();
    }

    public void updateWidget(WidgetEntity widget) {
        menuRepository.updateWidget(widget);
    }

    public void deleteWidget(WidgetEntity widget) {
        menuRepository.deleteWidget(widget);
    }

    public LiveData<List<WidgetEntity>> getCurrentWidgetList() {
        return currentWidgetList;
    }

    public MediatorLiveData<List<WidgetEntity>> getEditedMenuWidgetList() {
        return editedMenuWidgetList;
    }

    public void editWidget(WidgetEntity widget) {
        editedWidget.postValue(widget);
    }

    public LiveData<WidgetEntity> getEditedWidget() {
        return editedWidget;
    }
}
