package com.LocationLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

import com.LocationLibrary.db.DbModel;
import com.LocationLibrary.db.model.LocationsModel;



public final class DatabaseConstants {
	public static final String LOCATIONS_DATABASE_NAME = "locations.db";
	public static final int LOCATIONS_DATABASE_VERSION = 1;
	// Null for  default database Path
	public static final String LOCATIONS_DATABASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"pankaj";
	
	public static List<DbModel> createModels(){
		
		List<DbModel> models = new ArrayList<DbModel>();
		models.add(new LocationsModel());
		return models;
	}
}
