package com.example.baseremotecontroller.model.repository;

import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.baseremotecontroller.model.entity.PageEntity;
import com.example.baseremotecontroller.model.storage.DataStorage;

import java.util.List;

public class PageRepositoryImpl implements PageRepository{

    private static PageRepositoryImpl mInstance;

    private final DataStorage mDataStorage;
    private final MediatorLiveData<Long> mCurrentPageId;


    private PageRepositoryImpl(Application application) {
        mDataStorage = DataStorage.getInstance(application);
        mCurrentPageId = new MediatorLiveData<>();
    }

    public static PageRepositoryImpl getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new PageRepositoryImpl(application);
        }

        return mInstance;
    }

    @Override
    public void choosePage(long pageId) {
        if (mCurrentPageId.getValue() == null || mCurrentPageId.getValue() != pageId) {
            mCurrentPageId.postValue(pageId);
        }
    }

    @Override
    public void createPage(String pageName) {
        PageEntity page = new PageEntity();
        if (pageName != null) {
            page.name = pageName;
        }

        mDataStorage.addPage(page);
    }

    @Override
    public void removePage(long pageId) {
        mDataStorage.deletePage(pageId);
    }

    @Override
    public void updatePage(PageEntity page) {
        mDataStorage.updatePage(page);
    }

    @Override
    public LiveData<List<PageEntity>> getAllPages() {
        return mDataStorage.getAllPages();
    }

    @Override
    public LiveData<Long> getCurrentPageId() {
        return mCurrentPageId;
    }

    @Override
    public LiveData<PageEntity> getCurrentPage() {
        return Transformations.switchMap(mCurrentPageId,
                (Function<Long, LiveData<PageEntity>>) id -> mDataStorage.getPage(id));
    }

    @Override
    public LiveData<PageEntity> getPage(long id) {
        return mDataStorage.getPage(id);
    }
}
