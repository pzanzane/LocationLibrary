package com.LocationLibrary.locations.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.LocationLibrary.db.DbConfig;
import com.LocationLibrary.db.DbHelper;
import com.LocationLibrary.db.dao.LocationsDao;
import com.LocationLibrary.db.model.LocationsModel;
import com.google.android.gms.location.LocationClient;

public class LocationReceived extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d("Node", "LocationReceived ");
		Location location = intent
				.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);

		String str = "lat : " + location.getLatitude() + " lon : "
				+ location.getLongitude() + " Accuracy : "
				+ location.getAccuracy();

		Log.d("Node", str);

		LocationsModel model = new LocationsModel();
		model.setLattitude(location.getLatitude());
		model.setLongitude(location.getLongitude());
		model.setAccuracy(location.getAccuracy());
		model.setTimeStamp(location.getTime());

		RunnableLocationEntry runnable = RunnableLocationEntry.getInstance();
		runnable.setModel(model);
		runnable.setContext(context);

		Thread thread = new Thread(runnable);
		thread.start();

	} 
	static class RunnableLocationEntry implements Runnable {

		private static RunnableLocationEntry entry;

		public static RunnableLocationEntry getInstance() {
			if (entry == null) {
				entry = new RunnableLocationEntry();
			}
			return entry;
		}

		LocationsModel model;
		Context context;

		public void setModel(LocationsModel model) {
			this.model = model;
		}

		public void setContext(Context context) {
			this.context = context;
		}

		@Override
		public void run() {

			synchronized (entry) {
				
				SQLiteDatabase sq = DbHelper
						.getInstance().getSQLiteDatabase();
				
				LocationsDao dao = new LocationsDao(context, sq);
				
//				testTables(sq);
				
				dao.create(model);

			}
		}

	/*	private void testTables(SQLiteDatabase sq){
			
			Cursor cursor = sq.query("sqlite_master", null, null, null, null, null,
							null);
			
			Log.d("WASTE", "======================");
			Log.d("WASTE", "Databse Path : "+sq.getPath());
			while (cursor.moveToNext()) { 
				Log.d("WASTE", "TableName : "+cursor.getString(2));
			}
		}*/
	}
}
