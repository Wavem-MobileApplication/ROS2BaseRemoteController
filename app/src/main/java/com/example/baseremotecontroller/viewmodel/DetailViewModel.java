package com.example.baseremotecontroller.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.baseremotecontroller.model.entity.WidgetEntity;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {
    private ContentsViewModel contentsViewModel;

    private LiveData<List<WidgetEntity>> currentWidgets;
    private MutableLiveData<WidgetEntity> selectedWidget;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        contentsViewModel = new ContentsViewModel(application);
        selectedWidget = new MutableLiveData<>();
        currentWidgets = contentsViewModel.getCurrentWidgetList();
    }

    public void addWidget(WidgetEntity widget) {
        contentsViewModel.addWidget(widget);
    }

    public void createWidget(String widgetType) {
        contentsViewModel.createWidget(widgetType);
    }

    public void updateWidget(WidgetEntity widget) {
        contentsViewModel.updateWidget(widget);
    }

    public void deleteWidget(WidgetEntity widget) {
        contentsViewModel.deleteWidget(widget);
    }

    public void selectWidget(WidgetEntity widget) {
        selectedWidget.postValue(widget);
    }

    public LiveData<List<WidgetEntity>> getCurrentWidgets() {
        return currentWidgets;
    }

    public MutableLiveData<WidgetEntity> getSelectedWidget() {
        return selectedWidget;
    }
}
