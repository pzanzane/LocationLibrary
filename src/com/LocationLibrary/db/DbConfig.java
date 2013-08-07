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
		config.setDatabaseName(DatabaseConstants.LOCATIONS_DATABASE_NAME);
		config.setDatabasePath(DatabaseConstants.LOCATIONS_DATABASE_PATH);
		config.setDatabaseVersion(DatabaseConstants.LOCATIONS_DATABASE_VERSION);
		config.setModels(DatabaseConstants.createModels());
		return config;
	}

	@Override
	public void setDatabaseName(String databaseName) {
		this.databaseName=databaseName;		
	}

	@Override
	public void setDatabasePath(String databasePath) {
		this.databasePath=databasePath;
		
	}

	@Override
	public void setModels(List<DbModel> models) {
		this.models=models;
		
	}

	@Override
	public void setDatabaseVersion(int version) {
		databaseVersion=version;
		
	} 

}
