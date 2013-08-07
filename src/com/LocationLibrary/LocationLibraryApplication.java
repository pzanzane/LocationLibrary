package com.LocationLibrary;

import com.LocationLibrary.db.DbConfig;

import android.app.Application;

public abstract class LocationLibraryApplication extends Application {

	@Override
	public void onCreate() { 
		super.onCreate();
		
		LocationUtils.initializeLocations(getApplicationContext(),
				DbConfig.getInstance());
	}
}
