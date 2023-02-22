package com.example.baseremotecontroller.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "widget_table")
public class WidgetEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "widget_name")
    public String name;

    @ColumnInfo(name = "widget_type")
    @NonNull
    public String type;

    @ColumnInfo(name = "parent_id")
    @NonNull
    public long parentId;

    @ColumnInfo(name = "widget_data")
    public String data;
}
