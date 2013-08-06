package com.LocationLibrary.db;
import android.content.ContentValues;
import android.database.Cursor;

public interface DAO<T> {
    
    public static final String ID = "id";
    
    public T findByPrimaryKey(long id);
    public void create(T object);
    public void update(T object);
    public void createOrUpdate(T object);
    public void delete(long id);
    public boolean exists(long id);
        
    public T fromCursor(Cursor c);
    public ContentValues values(T t);
}