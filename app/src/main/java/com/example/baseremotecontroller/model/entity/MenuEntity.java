package com.example.baseremotecontroller.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu_table")
public class MenuEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "menu_name")
    public String name = "메뉴";

    @ColumnInfo(name = "parent_id")
    public long parentId;

    @ColumnInfo(name = "menu_creation_time")
    public long creationTime;
}
