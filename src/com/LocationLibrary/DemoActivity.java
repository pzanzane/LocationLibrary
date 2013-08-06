package com.LocationLibrary;

import com.google.android.gms.location.LocationRequest;

import android.app.Activity;
import android.os.Bundle;

public class DemoActivity extends Activity {

	LocationUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		utils = LocationUtils.getInstance();
		LocationUtils.initializeLocations(getApplicationContext());
		utils.startFetchingLocations(	getApplicationContext(),
										60,
										LocationRequest.PRIORITY_HIGH_ACCURACY,
										0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		utils.stopFetchingLocations();
		utils.clearLocationsTable();
	}
}
