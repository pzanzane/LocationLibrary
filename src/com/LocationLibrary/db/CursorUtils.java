package com.LocationLibrary.db;


import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

public class CursorUtils {

	public static Integer extractIntegerOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : c.getInt(columnIndex);	    
	}

	public static Long extractLongOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : c.getLong(columnIndex);
		
		//MKSN
	}

	public static int extractIntOrDefaultValue(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? 0 : c.getInt(columnIndex);
	}

	public static Float extractFloatOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : Float.valueOf(c.getString(columnIndex));
	}
	
	public static Double extractDoubleOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : c.getDouble(columnIndex);
	}

	public static boolean extractBoolean(Cursor c, String columnName) {
		int value = c.getInt(c.getColumnIndex(columnName));
		return value == 1;
	}

	public static byte[] extractBlobOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : c.getBlob(columnIndex);
	}

	public static String extractStringOrNull(Cursor c, String columnName) {
		int columnIndex = c.getColumnIndex(columnName);
		return c.isNull(columnIndex) ? null : c.getString(columnIndex);
	}

	public static String getSimpleSelectQuery(String tableName, String[] columnName) {
		String query = "SELECT ";
		int size = columnName.length;
		for (int i = 0; i < size - 1; i++) {
			query += "ifnull (" + columnName[i] + ",'null')" + " || ',' || ";
		}
		query += "ifnull (" + columnName[size - 1] + ",'null')";

		query += " as string FROM " + tableName;
		Log.d(	"size",
				query);
		return query;

	}

	public static String getSimpleSelectQuery(
			ArrayList<String> tableNames,
			ArrayList<String[]> columnName,
			ArrayList<String> whereArgs,
			ArrayList<String> selectionArgs) {
		int tablecount = tableNames.size(), whereArgsCount = whereArgs.size(), selectionArgsCount = selectionArgs.size();

		String query = "SELECT ";
		// get Number of Column Groups.Each String[] is a Group.
		int colsize = columnName.size();
		// To get each ColumnGroup from List
		for (int col = 0; col < colsize; col++) {
			if (!(col == colsize - 1)) {
				// Column Group from list
				String[] strArray = columnName.get(col);
				// Number of Columns in Each group
				int size = strArray.length;
				// To get each column From column group
				for (int i = 0; i < size; i++) {
					// Add it to query
					query += strArray[i] + " || ',' || ";
				}
			} else {
				// Last Column Group from list
				String[] strArray = columnName.get(col);
				// Number of Columns in Last group
				int size = strArray.length;
				// To get each column From last column group
				for (int i = 0; i < size - 1; i++) {
					// Add it to query
					query += strArray[i] + " || ',' || ";
				}
				query += strArray[size - 1] + " as string";
			}
		}

		// Write From clause
		query += " FROM ";
		for (int i = 0; i < tablecount; i++) {
			String s = tableNames.get(i);
			query += s;
			if (!(i == tablecount - 1)) {
				query += ",";
			}

		}

		// Write where clause
		query += " WHERE ";
		for (int i = 0; i < whereArgsCount; i++) {
			query += whereArgs.get(i) + " = " + selectionArgs.get(i);
			if (!(i == whereArgsCount - 1))
				query += " AND ";
		}
		return query;
	}

}
