package com.example.baseremotecontroller.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.baseremotecontroller.model.entity.PageEntity;

import java.util.List;

@Dao
public abstract class PageDao implements BaseDao<PageEntity> {

    @Query("SELECT * FROM page_table")
    public abstract LiveData<List<PageEntity>> getAllPages();

    @Query("SELECT * FROM page_table where id = :id")
    public abstract LiveData<PageEntity> getPage(long id);

    @Query("DELETE FROM page_table where id = :id")
    public abstract void removePage(long id);
}
