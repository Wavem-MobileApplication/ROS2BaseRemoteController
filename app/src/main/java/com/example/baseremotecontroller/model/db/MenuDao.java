package com.example.baseremotecontroller.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.baseremotecontroller.model.entity.MenuEntity;

import java.util.List;

@Dao
public abstract class MenuDao implements BaseDao<MenuEntity> {

    @Query("SELECT * FROM menu_table ORDER BY menu_creation_time ASC")
    public abstract LiveData<List<MenuEntity>> getAllMenus();

    @Query("SELECT * FROM menu_table where id = :id")
    public abstract LiveData<MenuEntity> getMenu(long id);

    @Query("SELECT * FROM menu_table where parent_id = :parentId")
    public abstract List<MenuEntity> getMenusFromPage(long parentId);

    @Query("DELETE FROM menu_table where id = :id")
    public abstract void removeMenu(long id);

    @Query("DELETE FROM menu_table where parent_id = :parentId")
    public abstract void removeMenusFromPage(long parentId);
}
