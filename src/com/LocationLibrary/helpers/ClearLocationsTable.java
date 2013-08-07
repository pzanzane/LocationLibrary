package com.LocationLibrary.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.LocationLibrary.DatabaseConstants;
import com.LocationLibrary.db.DbConfig;
import com.LocationLibrary.db.DbHelper;
import com.LocationLibrary.db.IDbConfiguration;
import com.google.android.gms.internal.dv;


public final class ClearLocationsTable{
	
	Context context;
	IDbConfiguration config;
	
	public ClearLocationsTable(Context context,IDbConfiguration config){
		this.context = context;
		this.config=config;
	}
	public void clear(){
		
		
		Thread t = new Thread(new RunnableClearTables(context));
		t.start();
		
	}
	
	static class RunnableClearTables implements Runnable{

		Context context;
		IDbConfiguration config;
		
		public RunnableClearTables(Context context) {
			this.context = context;
		}
		
		public void setConfig(IDbConfiguration config) {
			this.config = config;
		}

		@Override
		public void run() {
			
			SQLiteDatabase sq = DbHelper.getInstance(context,config).getSQLiteDatabase();
			
			sq.delete(DatabaseConstants.LOCATIONS_DATABASE_NAME, null, null);
			
		}
		
	}

}
