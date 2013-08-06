package com.LocationLibrary.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.LocationLibrary.db.DAO;
import com.LocationLibrary.db.DbModel;
import com.LocationLibrary.db.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseDAO, performs basic CRUD operations.
 * 
 * @param <T>
 *            the generic type of DbModel
 */
public abstract class BaseDAO<T extends DbModel> implements DAO<T> {

	/** The Constant TAG. */
	private static final String TAG = "BaseDAO";

	/** The db. */
	protected final SQLiteDatabase db;

	/** The context. */
	protected final Context context;

	/**
	 * Instantiates a new base dao.
	 * 
	 * @param context
	 *            the context
	 * @param db
	 *            the db
	 */
	public BaseDAO(Context context, SQLiteDatabase db) {
		this.context = context;
		this.db = db;
	}

	/**
	 * Gets the table name for DAO.
	 * 
	 * @return the table name
	 */
	public abstract String getTableName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opendroid.db.dao.DAO#fromCursor(android.database.Cursor)
	 */
	public abstract T fromCursor(Cursor c);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opendroid.db.dao.DAO#values(java.lang.Object)
	 */
	public abstract ContentValues values(T t);

	/**
	 * Checks if table is not empty.
	 * 
	 * @return true, if is not empty
	 * @throws DAOException
	 *             the dAO exception
	 */
	public boolean isNotEmpty(){
		Cursor c = null;

			c = db.rawQuery("select " + ID + " from " + getTableName(),
							null);
			if(c==null)
				return false;
			
			boolean bool = c.moveToFirst();
			c.close();
			 
			return bool;
		 
	}

	/**
	 * Find resord by primary key.
	 * 
	 * @param id
	 *            the id
	 * @return the t
	 * @throws DAOException
	 *             the dAO exception
	 */
	public T findByPrimaryKey(long id) {
		Cursor c = null;
		T t = null;
 
			c = db.rawQuery("select * from " + getTableName() + " where " + ID + " = ?",
							whereArgsForId(id));
			
			if(c==null)
				return t;
			
			if (c.moveToFirst()) {
				t = fromCursor(c);
			}		 
		return t;
	}

	/**
	 * Find first by field.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param value
	 *            the value
	 * @return the t
	 * @throws DAOException
	 *             the dAO exception
	 */
	public T findFirstByField(String fieldName, String value){
		Cursor c = null;
		T t = null;
 
			String q = "select * from " + getTableName() + " where " + fieldName + " = ?";
			Log.d(	TAG,
					q);
			c = db.rawQuery(q,
							new String[] { value });
			
			if(c==null)
				return t;
			
			if (c.moveToFirst()) {
				t = fromCursor(c);
			}
		 
		return t;
	}

//	public List<T> findAllByFields(String[] fields,String[] values){
//		List<T> lst = null;
//		String selection="";
//		for(String str : fields){
//			if(selection.equalsIgnoreCase("")){
//				selection = str+" = ?";
//			}
//			selection+=" AND "+str+" = ?";
//		}
////		query(getTableName(), null, fie, selectionArgs, groupBy, having, orderBy)
//		return lst;
//	}
	/**
	 * Select all.
	 * 
	 * @return the list
	 */
	public List<T> selectAll() {
		Cursor c = null;
		List<T> result = null;
		try {
			c = db.rawQuery("select * from " + getTableName(),
							null);
			result = new ArrayList<T>();
			if (c.moveToFirst()) {
				do {
					Log.d(	TAG,
							"COLUMN : "
									+ c.getString(0) + " 1: " + c.getString(1) + " 2: "
									+ c.getString(2));
					result.add(fromCursor(c));
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * Find all by field.
	 * 
	 * @param fieldName
	 *            - field name to search by
	 * @param value
	 *            - the value of the field
	 * @param orderConditions
	 *            - the "order by" sentence. May be <b>null</b>.
	 * @return the list
	 */
	public List<T> findAllByField(String fieldName, String value, String orderConditions) {
		Cursor c = null;
		List<T> result = null;
		try {
			c = db.rawQuery("select * from "
									+ getTableName() + " where " + fieldName + " = ? "
									+ StringUtils.safe(orderConditions),
							new String[] { value });
			result = new ArrayList<T>();
			if (c.moveToFirst()) {
				do {
					result.add(fromCursor(c));
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opendroid.db.dao.DAO#create(java.lang.Object)
	 */
	public void create(T model){

		int id = (int) db.insert(	getTableName(),
									"0.0",
									values(model));
		 
		if (id != -1) {
			model.setId(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opendroid.db.dao.DAO#update(java.lang.Object)
	 */
	public void update(T model){

		db.update(	getTableName(),
					values(model),
					ID + " = ?",
					whereArgsForId(model.getId()));
	}
	
	public int updateAndGetRowsEffected(T model){
		return db.update(getTableName(), values(model), ID + " = ?",
				whereArgsForId(model.getId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opendroid.db.dao.DAO#createOrUpdate(java.lang.Object)
	 */
	public void createOrUpdate(T model){
		if (exists(model.getId())) {
			update(model);
		} else {
			create(model);
		}
	}

	/**
	 * Update.
	 * 
	 * @param models
	 *            the models
	 * @throws DAOException
	 *             the dAO exception
	 */
	public void update(ArrayList<T> models){
		for (T model : models) {
			update(model);
		}
	}

	/**
	 * Delete.
	 * 
	 * @param id
	 *            the id
	 * @throws DAOException
	 *             the dAO exception
	 */
	public void delete(long id){
		db.delete(	getTableName(),
					" " + ID + " = ?",
					whereArgsForId(id));
	}

	/**
	 * Delete all.
	 * 
	 * @throws DAOException
	 *             the dAO exception
	 */
	public void deleteAll(){
		db.delete(	getTableName(),
					null,
					null);
	}

	/**
	 * Delete by field.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param fieldValue
	 *            the field value
	 */
	public void deleteByField(String fieldName, String fieldValue) {
		db.delete(	getTableName(),
					" " + fieldName + " = ?",
					new String[] { fieldValue });
	}

	/**
	 * Exists.
	 * 
	 * @param id
	 *            the id
	 * @return true, if successful
	 * @throws DAOException
	 *             the dAO exception
	 */
	public boolean exists(long id) {
		Cursor c = null;
 
			c = db.rawQuery("select _id from " + getTableName() + " where " + ID + " = ?",
							whereArgsForId(id));
			
			if(c==null)
				return false;
			
			boolean bool = c.moveToFirst();
			c.close();
			return bool;
		 
	}

	/**
	 * Find all.
	 * 
	 * @return the list
	 */
	public List<T> findAll() {
		Cursor c = null;
		List<T> result = null;
		try {
			c = db.rawQuery("select * from " + getTableName(),
							null);
			result = new ArrayList<T>();
			if (c.moveToFirst()) {
				do {
					result.add(fromCursor(c));
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * Find all.
	 * 
	 * @param orderConditions
	 *            the order conditions
	 * @return the list
	 */
	protected List<T> findAll(String orderConditions) {
		Cursor c = null;
		List<T> result = null;
		try {
			c = db.rawQuery("select * from "
									+ getTableName() + ' ' + StringUtils.safe(orderConditions),
							null);
			result = new ArrayList<T>();
			if (c.moveToFirst()) {
				do {
					result.add(fromCursor(c));
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public List<T> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Cursor c = null;
		List<T> result = null;
		
		try {
			c = db.query(getTableName(), null, selection, selectionArgs, groupBy, having, orderBy);
			result = new ArrayList<T>();
			if (c.moveToFirst()) {
				do {
					result.add(fromCursor(c));
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	
	public Cursor cursorQuery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Cursor c = null; 
			c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}
	
	/**
	 * Where args for id.
	 * 
	 * @param id
	 *            the id
	 * @return the string[]
	 */
	protected String[] whereArgsForId(long id) {
		return new String[] { String.valueOf(id) };
	}

	/**
	 * Convert <code>{1, 2, 3}</code> to <code>"1,2,3"</code>.
	 * 
	 * @param ids
	 *            the ids
	 * @return the string
	 */
	protected String idArrayToString(int[] ids) {
		StringBuilder sqlFragment = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sqlFragment.append(ids[i]);
			if (i < ids.length - 1) {
				sqlFragment.append(',');
			}
		}
		return sqlFragment.toString();
	}

	/**
	 * Convert all cursor lines to a list of ordersModel objects.
	 * 
	 * @param cursor
	 *            the cursor
	 * @return the list
	 */
	protected List<T> allFromCursor(Cursor cursor) {
		ArrayList<T> result = new ArrayList<T>();
		if (cursor.moveToFirst()) {
			do {
				result.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}
		return result;
	}

	public Cursor getCursorByField(String fieldName, String value, String orderConditions) {
		Cursor c = null;

		c = db.rawQuery("select * from "
								+ getTableName() + " where " + fieldName + " = ? "
								+ StringUtils.safe(orderConditions),
						new String[] { value });

		return c;
	}

	public Cursor getAllCursor() {
		Cursor c = null;

		c = db.rawQuery("SELECT * FROM " + getTableName(),
						null);

		return c;
	}

	protected Cursor getAllCursor(String orderConditions) {
		Cursor c = null;

		c = db.rawQuery("select * from " + getTableName() + ' ' + StringUtils.safe(orderConditions),
						null);

		return c;
	}
} 
