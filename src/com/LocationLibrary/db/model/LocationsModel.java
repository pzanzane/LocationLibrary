package com.LocationLibrary.db.model;

import com.LocationLibrary.db.DbModel;
import com.LocationLibrary.db.dao.LocationsDao;

public class LocationsModel implements DbModel {

	private long id;
	private double lattitude;
	private double longitude;
	private float accuracy;
	private long timeStamp;
	

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id=id;
	}
	
	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String getTableName() {
		return LocationsDao.TABLE_NAME;
	}

	@Override
	public String getCreateStatement() {
		return LocationsDao.CREATE_TABLE;
	}

}
