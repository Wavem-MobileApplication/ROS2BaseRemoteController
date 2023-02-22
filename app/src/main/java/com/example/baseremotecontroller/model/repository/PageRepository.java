package com.example.baseremotecontroller.model.repository;

import androidx.lifecycle.LiveData;

import com.example.baseremotecontroller.model.entity.PageEntity;

import java.util.List;

public interface PageRepository {

    void choosePage(long pageId);

    void createPage(String pageName);

    void removePage(long pageId);

    void updatePage(PageEntity page);

    LiveData<List<PageEntity>> getAllPages();

    LiveData<Long> getCurrentPageId();

    LiveData<PageEntity> getCurrentPage();

    LiveData<PageEntity> getPage(long id);
}
