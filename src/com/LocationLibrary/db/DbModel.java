package com.LocationLibrary.db;

// TODO: Auto-generated Javadoc
/**
 * The Interface DbModel, to be implemented for mapping table with db table .
 */
public interface DbModel {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId();

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id);
	
	/**
	 * returns table name to which the ordersModel corresponds.
	 *
	 * @return the table name
	 */
	public String getTableName();

	/**
	 * Gets the creates the statement for table.
	 *
	 * @return the creates the statement
	 */
	public String getCreateStatement();
}