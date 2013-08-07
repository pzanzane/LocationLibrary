package com.LocationLibrary.db;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class DbConfiguration.
 */
public interface IDbConfiguration{
   
	String getDatabaseName();
  
	String getDatabasePath();
  
	List<DbModel> getModels();
  
	int getDatabaseVersion();
	
	void setDatabaseName(String databaseName);
	  
	void setDatabasePath(String databasePath);
  
	void setModels(List<DbModel> models);
  
	void setDatabaseVersion(int version);
}
