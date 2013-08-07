package com.LocationLibrary.db;

import java.util.List;

import com.LocationLibrary.DatabaseConstants;

public class DbConfig implements IDbConfiguration {

	private String databaseName;
	private String databasePath;
	private int databaseVersion;
	private List<DbModel> models;
	
	@Override
	public String getDatabaseName() { 
		return databaseName;
	}

	/**
	 * databasePath should be null if tou want default database path
	 */
	@Override
	public String getDatabasePath() { 
		return databasePath;
	}

	@Override
	public List<DbModel> getModels() { 
		return models;
	}

	@Override
	public int getDatabaseVersion() { 
		return databaseVersion;
	}

 
	public static IDbConfiguration getInstance() {
		
		DbConfig config = new DbConfig();
		config.databaseName=DatabaseConstants.LOCATIONS_DATABASE_NAME;
		config.databasePath=DatabaseConstants.LOCATIONS_DATABASE_PATH;
		config.databaseVersion=DatabaseConstants.LOCATIONS_DATABASE_VERSION;
		config.models=DatabaseConstants.createModels();
		return config;
	} 

}
