package com.LocationLibrary.helpers;

import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;

import com.LocationLibrary.Constants;
import com.LocationLibrary.db.LocationsDbHelper;


public final class ClearLocationsTable{
	
	Context context;
	
	
	public ClearLocationsTable(Context context){
		this.context = context;
	}
	public void clear(){
		
		
		Thread t = new Thread(new RunnableClearTables(context));
		t.start();
		
	}
	
	static class RunnableClearTables implements Runnable{

		Context context;
		
		public RunnableClearTables(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			
			SQLiteDatabase sq = LocationsDbHelper.getInstance(context, Constants.LOCATIONS_DATABASE_NAME).getSQLiteDatabase();
			
			sq.delete(Constants.LOCATIONS_DATABASE_NAME, null, null);
			
		}
		
	}

}
