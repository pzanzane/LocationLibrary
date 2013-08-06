package com.LocationLibrary;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.LocationLibrary.db.DbConfiguration;
import com.LocationLibrary.db.LocationsDbHelper;
import com.LocationLibrary.db.DbModel;
import com.LocationLibrary.db.dao.LocationsDao;
import com.LocationLibrary.db.model.LocationsModel;
import com.LocationLibrary.locations.receiver.LocationReceived;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class LocationUtils implements ConnectionCallbacks, OnConnectionFailedListener{

	private static LocationUtils utils;
	private long intervalInMillis;
	private int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
	private int minDisplacementMeters=0;
	private String databaseName=null;
	
	private LocationClient client;
	private Context context;
	
	private LocationUtils(String databaseName){
		this.databaseName = databaseName;
	}
	
	public static LocationUtils getInstance(){
		
		if(utils==null)
			utils = new LocationUtils(Constants.LOCATIONS_DATABASE_NAME);
		  
		return utils;
	}
	public static void initializeLocations(Context context){
		
		List<DbModel> list = new ArrayList<DbModel>();
		list.add(new LocationsModel());
		
		DbConfiguration.Builder builder = new DbConfiguration.Builder();
		builder.setDatabaseName(Constants.LOCATIONS_DATABASE_NAME);
		
		builder.setModels(list);
		
		LocationsDbHelper.getInstance(context, builder.build());
	}
	
	public void startFetchingLocations(Context context,int intervalInSeconds,int priority,int minDisplacementInMeters){
		this.intervalInMillis=(intervalInSeconds*1000l);
		this.priority=priority;
		this.minDisplacementMeters=minDisplacementInMeters;
		this.context=context;
		
		client = new LocationClient(context,
									this,
									this);
		client.connect();		
	}
	
	public void stopFetchingLocations(){
		if(client!=null && client.isConnected())
			client.disconnect();
	}
	
	public void clearLocationsTable(){
		
	}
	@Override
	public void onConnectionFailed(ConnectionResult arg0) { 
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.i("Node", "onConnected");
		LocationRequest request = LocationRequest.create();

		request.setInterval(intervalInMillis);
		request.setFastestInterval(intervalInMillis);
		request.setPriority(priority);
		request.setSmallestDisplacement(minDisplacementMeters);

		client.requestLocationUpdates(	request,
										getPendingIntent(context));
		
	}

	@Override
	public void onDisconnected() {
		utils=null;
	}
	
	private PendingIntent getPendingIntent(Context context) {

		Intent intent = new Intent(	context,
									LocationReceived.class);
		
		return PendingIntent.getBroadcast(	context,
											0,
											intent,
											PendingIntent.FLAG_UPDATE_CURRENT);

	}
 	
	/**
	 * @param invalidateTimeInSeconds
	 * This parameter Should be greater than intervalInSeconds to be used effectively.
	 * @return Latest valid LocationsModel
	 */
	public LocationsModel getLatestLocation(int invalidateTimeInSeconds){
		
		LocationsDao dao = new LocationsDao(context,
											LocationsDbHelper.getInstance(context,databaseName)
													.getSQLiteDatabase());
		
		
		String query = "SELECT * FROM "+LocationsDao.TABLE_NAME
						+" WHERE "+LocationsDao.TIMESTAMP 
						+" IN ("+"SELECT MAX("+LocationsDao.TIMESTAMP+") FROM "+LocationsDao.TABLE_NAME+")"
						+" AND "+LocationsDao.TIMESTAMP+">"+(System.currentTimeMillis() - (invalidateTimeInSeconds * 1000));
		
		SQLiteDatabase sq = LocationsDbHelper.getInstance(context,databaseName).getSQLiteDatabase();
		Cursor c = sq.rawQuery(query, null);
		
		if (c.moveToFirst()) {
			LocationsModel model = dao.fromCursor(c);
			return model;
		}
		
		return null;
	}
}
