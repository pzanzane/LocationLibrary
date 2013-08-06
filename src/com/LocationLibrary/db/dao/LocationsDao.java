package com.LocationLibrary.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.LocationLibrary.db.CursorUtils;
import com.LocationLibrary.db.model.LocationsModel;

public class LocationsDao extends BaseDAO<LocationsModel> {

	public static String TABLE_NAME="locations_table";
	
	public static String ID="id";
	public static String LATTITUDE="lattitude";
	public static String LONGITUDE="longitude";
	public static String ACCURACY="accuracy";
	public static String TIMESTAMP="timestamp";
	
	public static String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
								+"("
								+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
								+LATTITUDE+" NUMBER,"
								+LONGITUDE+" NUMBER,"
								+ACCURACY+" NUMBER,"
								+TIMESTAMP+" INTEGER"
								+");";
			
	public LocationsDao(Context context, SQLiteDatabase db) {
		super(	context,
				db);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public LocationsModel fromCursor(Cursor c) {
		
		LocationsModel model = new LocationsModel();
		model.setId(CursorUtils.extractLongOrNull(c, ID));
		model.setLattitude(CursorUtils.extractDoubleOrNull(c, LATTITUDE));
		model.setLongitude(CursorUtils.extractDoubleOrNull(c, LONGITUDE));
		model.setAccuracy(CursorUtils.extractFloatOrNull(c, ACCURACY));
		model.setTimeStamp(CursorUtils.extractLongOrNull(c, TIMESTAMP));
		
		return model;
	}

	@Override
	public ContentValues values(LocationsModel t) {
		
		ContentValues values = new ContentValues();
		
		if(t.getId()>0)
			values.put(ID, t.getId());
		
		values.put(LATTITUDE, t.getLattitude());
		values.put(LONGITUDE, t.getLongitude());
		values.put(ACCURACY, t.getAccuracy());
		values.put(TIMESTAMP, t.getTimeStamp());
		
		return values;
	}

}
