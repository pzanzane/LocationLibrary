package com.LocationLibrary;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.LocationLibrary.db.DbConfig;
import com.LocationLibrary.db.DbHelper;
import com.LocationLibrary.db.IDbConfiguration;
import com.LocationLibrary.db.dao.LocationsDao;
import com.LocationLibrary.db.model.LocationsModel;
import com.LocationLibrary.helpers.ClearLocationsTable;
import com.LocationLibrary.locations.receiver.LocationReceived;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class LocationClientUtils implements ConnectionCallbacks, OnConnectionFailedListener{

	private static LocationClientUtils utils;
	private long intervalInMillis;
	private int priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
	private int minDisplacementMeters=0;
 	
	private LocationClient client; 
	private PendingIntent pedingIntent;
	
	private LocationClientUtils(Context context){
		pedingIntent = getPendingIntent(context);
	}
	
	public static LocationClientUtils getInstance(Context context){
		
		if(utils==null)
			utils = new LocationClientUtils(context);
		  
		return utils;
	}
	public static void initializeLocations(Context context,IDbConfiguration config){
 		DbHelper.instanciateDatabase(context, config);
	}
	
	public void startFetchingLocations(Context context,int intervalInSeconds,int priority,int minDisplacementInMeters){
		this.intervalInMillis=(intervalInSeconds*1000l);
		this.priority=priority;
		this.minDisplacementMeters=minDisplacementInMeters;
		
		client = new LocationClient(context,
									this,
									this);
		client.connect();		
	}
	
	public void stopFetchingLocations(){
		
		Log.d("Node", "client connected : "+client.isConnected());
		
		if(client!=null && client.isConnected()){
			Log.d("Node", "client Disconnecting");
			client.removeLocationUpdates(pedingIntent);
			client.disconnect();
		}
	}
	
	public void clearLocationsTable(Context context){
		
		ClearLocationsTable clearTable = new ClearLocationsTable(context, DbConfig.getInstance());
		clearTable.clear();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) { 
		Log.d("Node", "Connetcion Failed");
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
										pedingIntent);
		
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
	public LocationsModel getLatestLocation(Context context,int invalidateTimeInSeconds){
		
		LocationsDao dao = new LocationsDao(context,
				DbHelper.getInstance(context,DbConfig.getInstance())
													.getSQLiteDatabase());
		
		
		String query = "SELECT * FROM "+LocationsDao.TABLE_NAME
						+" WHERE "+LocationsDao.TIMESTAMP 
						+" IN ("+"SELECT MAX("+LocationsDao.TIMESTAMP+") FROM "+LocationsDao.TABLE_NAME+")"
						+" AND "+LocationsDao.TIMESTAMP+">"+(System.currentTimeMillis() - (invalidateTimeInSeconds * 1000));
		
		SQLiteDatabase sq = DbHelper.getInstance(context,DbConfig.getInstance()).getSQLiteDatabase();
		Cursor c = sq.rawQuery(query, null);
		
		if (c.moveToFirst()) {
			LocationsModel model = dao.fromCursor(c);
			return model;
		}
		
		return null;
	}

	@Override
	public void onDisconnected() {
		
		Log.d("Node", "Disconnected");
		
	}
}
