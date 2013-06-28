package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
	/* Our database variables */
	private static final String	DATABASE_NAME			= "DisasterReporter";
	private static final int	DATABASE_VERSION		= 19;
	/* Our tables and fields */
	public static final String	INFO_TABLE				= "saved_info";
	public static final String	SETTINGS_TABLE			= "saved_settings";
	// public static final String SAVED_STATES_TABLE = "saved_states";
	public static final String	SESSION_VARIABLES_TABLE	= "session_variables";
	
	// private static final String PAGE = "relevant_page";
	private static final String	TYPE					= "record_type";
	private static final String	CONTENTS				= "record_content";
	private static final String	ID						= "id";
	
	public static String		UNIQUE_INFO				= "attuid";
	
	// constructor
	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * This function will create our database and table. First we define strings
	 * containing our SQL queries, and then we execute them.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Build the Info table
		String create_saved_info = "CREATE TABLE " + INFO_TABLE + "(" + TYPE + " TEXT," + CONTENTS + " TEXT" + ", " + ID + " INTEGER NOT NULL PRIMARY KEY autoincrement)";
		// Execute the query
		db.execSQL(create_saved_info);
		
		// Build the Settings table
		String create_saved_settings = "CREATE TABLE " + SETTINGS_TABLE + "(" + TYPE + " TEXT PRIMARY KEY," + CONTENTS + " TEXT)";
		// Execute the query
		db.execSQL(create_saved_settings);
		
		// Build the Session variable table
		String create_session_variables = "CREATE TABLE " + SESSION_VARIABLES_TABLE + "(" + TYPE + " TEXT PRIMARY KEY," + CONTENTS + " TEXT)";
		// Execute the query
		db.execSQL(create_session_variables);
	}
	
	/**
	 * This function will handle upgrade of our database. In this case we will
	 * just drop the older table if it exists and create a new one.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + INFO_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE);
		// db.execSQL("DROP TABLE IF EXISTS " + SAVED_STATES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SESSION_VARIABLES_TABLE);
		
		// Then we run the onCreate() method again //
		onCreate(db);
	}
	
	/**
	 * Method to get information in the table that matches the supplied info
	 * type.
	 * 
	 * @param type
	 * @return A String[] with all of the contents of the records
	 *         which have types matching the supplied type
	 */
	public String[] getInfoByType(String type) {
		ArrayList<String> temp_array = new ArrayList<String>();
		String[] match_array = new String[0];
		// The SQL Query
		String sqlQuery = "SELECT * FROM " + INFO_TABLE + " WHERE " + TYPE + "=\"" + type + "\"";
		// Define database and cursor
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(sqlQuery, null);
		
		// Loop through the result and add it to the temp_array
		if (c.moveToFirst()) {
			do {
				temp_array.add(c.getString(c.getColumnIndexOrThrow(CONTENTS)));
			} while (c.moveToNext());
		}
		// Close the cursor
		if (c != null && !c.isClosed()) c.close();
		// Close the database connection
		db.close();
		// Transfer from the ArrayList to string array
		match_array = temp_array.toArray(match_array);
		// Return the string array
		return match_array;
	}
	
	/**
	 * Method to get information in the table that matches the supplied info
	 * type.
	 * 
	 * @param type
	 * @return A String with the first of the contents of the records which have
	 *         types matching the supplied type
	 */
	public String getFirstInfoOfType(String type) {
		String match = "";
		// The SQL Query
		String sqlQuery = "SELECT * FROM " + INFO_TABLE + " WHERE " + TYPE + "=\"" + type + "\"";
		// Define database and cursor
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(sqlQuery, null);
		
		// Loop through the result and add it to the temp_array
		if (c.moveToFirst()) {
			match = c.getString(c.getColumnIndexOrThrow(CONTENTS));
		}
		// Close the cursor
		if (c != null && !c.isClosed()) c.close();
		// Close the database connection
		db.close();
		// Return the string
		return match;
	}
	
	/**
	 * Add a record to the table with type and contents as supplied. Duplicates
	 * will not be added, and adding a display name when one is already saved
	 * will result in an overwrite.
	 * 
	 * @param type
	 *            The type of info to be inserted. This should be either
	 *            "location", "email", or "display_name"
	 * @param contents
	 *            The info that is being inserted as the supplied type. Location
	 *            should be entered as "latitude longitude", email should be
	 *            entered as "address@host.suffix", and display name should be
	 *            entered as "name". Only one display name can be added at a
	 *            time
	 */
	public void addInfo(String type, String contents) {
		String[] matches = getInfoByType(type);
		
		if (contents.equals("")) return;
		else if (type.equals(UNIQUE_INFO)) {
			if (matches.length > 0) removeInfo(type, null);
		} else {
			for (int i = 0; i < matches.length; i++) {
				if (matches[i].equals(contents)) return;
			}
		}
		
		ContentValues values = new ContentValues();
		values.put(TYPE, type);
		values.put(CONTENTS, contents);
		
		/* Inserting the entry */
		SQLiteDatabase db = this.getWritableDatabase();
		// db.insert(INFO_TABLE, null, values);
		db.insertWithOnConflict(INFO_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		// close the database connection
		db.close();
	}
	
	public void removeInfo(String type, String contents) {
		SQLiteDatabase db = this.getWritableDatabase();
		String values = TYPE + "=\"" + type + "\"";
		if (contents != null) {
			values += " AND " + CONTENTS + "=\"" + contents + "\"";
		}
		
		/* Deleting the entry */
		db.delete(INFO_TABLE, values, null);
		// close the database connection
		db.close();
	}
	
	/**
	 * Update the value of a session variable in the database. This approximates
	 * session variables that are erased after the end of each session.
	 * 
	 * @param session_variable_name
	 *            The name of the variable that is being stored.
	 * @param value
	 *            The info that is being updated or stared into the supplied
	 *            variable. Whatever the value is, it should be wrapped in a
	 *            String.
	 */
	public void sessionSet(String session_variable_name, String value) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TYPE, session_variable_name);
		values.put(CONTENTS, value);
		/* Inserting the entry */
		db.insertWithOnConflict(SESSION_VARIABLES_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		// close the database connection
		db.close();
	}
	
	/**
	 * Get the value of a session variable from the database. This approximates
	 * session variables that are erased after the end of each session.
	 * 
	 * @param session_variable_name
	 *            The name under which the requested session variable is stored
	 * @return Returns the String value stored under the passed variable name.
	 * @throws SQLiteException
	 */
	public String sessionGet(String session_variable_name)
			throws SQLiteException {
		String match = "";
		// The SQL Query
		String sqlQuery = "SELECT * FROM " + SESSION_VARIABLES_TABLE + " WHERE " + TYPE + "=\"" + session_variable_name + "\"";
		// Define database and cursor
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(sqlQuery, null);
		// Loop through the result and add it to the temp_array
		if (c.moveToFirst()) {
			do {
				match = c.getString(c.getColumnIndexOrThrow(CONTENTS));
				// match = "true";
			} while (c.moveToNext());
		}
		// Close the cursor
		if (c != null && !c.isClosed()) c.close();
		// Close the database connection
		db.close();
		// Return the string array
		return match;
	}
	
	/**
	 * Clear all session variables from the database. This approximates session
	 * variables that are erased after the end of each session.
	 */
	public void sessionUnset() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		/* clearing the table */
		db.delete(SESSION_VARIABLES_TABLE, "", null);
		// close the database connection
		db.close();
	}
	
	/**
	 * Clear a single session variable from the database. This approximates
	 * session variables that are erased after the end of each session.
	 */
	public void sessionUnset(String which) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		/* clearing the table */
		db.delete(SESSION_VARIABLES_TABLE, TYPE + "=\"" + which + "\"", null);
		// close the database connection
		db.close();
	}
	
	/**
	 * Update the value of a setting in the settings database.
	 * 
	 * @param setting
	 *            The type of setting to be updated. This should be either
	 *            "remember_me" or "send_diagnostics"
	 * @param new_value
	 *            The info that is being updated into the supplied type. Should
	 *            be either "true" or "false".
	 */
	public void setSetting(String setting, boolean new_value) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TYPE, setting);
		if (new_value) values.put(CONTENTS, "true");
		else values.put(CONTENTS, "false");
		/* Inserting the entry */
		db.insertWithOnConflict(SETTINGS_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		// close the database connection
		db.close();
	}
	
	/**
	 * 
	 * @param type
	 *            The setting that will be checked in the database
	 * @return Returns the boolean stored value for the supplied setting.
	 * @throws SQLiteException
	 */
	public boolean checkSetting(String type) throws SQLiteException {
		String match = "";
		// The SQL Query
		String sqlQuery = "SELECT * FROM " + SETTINGS_TABLE + " WHERE " + TYPE + "=\"" + type + "\"";
		// Define database and cursor
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(sqlQuery, null);
		// Loop through the result and add it to the temp_array
		if (c.moveToFirst()) {
			do {
				match = c.getString(c.getColumnIndexOrThrow(CONTENTS));
				// match = "true";
			} while (c.moveToNext());
		}
		// Close the cursor
		if (c != null && !c.isClosed()) c.close();
		// Close the database connection
		db.close();
		// Return the string array
		return match.equals("true");
	}
	
	// /**
	// * This method returns the string that would have been saved the previous
	// * call of addSavedState(page, type, content). If there is nothing saved,
	// * this method returns "", that is, an empty string.
	// *
	// * @param page
	// * The page of which the view whose state is desired is part
	// * @param tag
	// * The unique tag used to save the specific view whose state is
	// * desired
	// * @return The state previously saved as a string, or an empty string ("")
	// * if nothing is saved
	// */
	// public String getSavedStateByPageAndType(String page, String tag) {
	// String match = "";
	// // The SQL Query
	// String sqlQuery = "SELECT * FROM " + SAVED_STATES_TABLE + " WHERE " +
	// PAGE + "=\"" + page + "\" AND " + TYPE + "=\"" + tag + "\"";
	// // Define database and cursor
	// SQLiteDatabase db = this.getWritableDatabase();
	// Cursor c = db.rawQuery(sqlQuery, null);
	// // Loop through the result and add it to the temp_array
	// if (c.moveToFirst()) {
	// do {
	// match = c.getString(c.getColumnIndexOrThrow(CONTENTS));
	// // match = "true";
	// } while (c.moveToNext());
	// }
	// // Close the cursor
	// if (c != null && !c.isClosed()) c.close();
	// // Close the database connection
	// db.close();
	// // Return the string array
	// return match;
	// }
	//
	// public void addSavedState(String page, String type, String contents) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(PAGE, page);
	// values.put(TYPE, type);
	// values.put(CONTENTS, contents.equals("") ? "null" : contents);
	// db.delete(SAVED_STATES_TABLE, PAGE + "=\"" + page + "\" AND " + TYPE +
	// "=\"" + type + "\"", null);
	// db.insertWithOnConflict(SAVED_STATES_TABLE, null, values,
	// SQLiteDatabase.CONFLICT_REPLACE);
	// // close the database connection
	// db.close();
	// }
	
	/**
	 * This method deletes all of the records in the table whose name is
	 * provided.
	 * 
	 * @param which
	 *            The name of the table all of whose values will be deleted. Use
	 *            carefully!
	 */
	public void clearTable(String which) throws SQLiteException {
		SQLiteDatabase db = this.getWritableDatabase();
		
		/* clearing the table */
		// db.execSQL("DELETE * FROM " + which);
		db.delete(which, "", null);
		// close the database connection
		db.close();
	}
	
	public void clearAllTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.execSQL("DROP TABLE IF EXISTS " + INFO_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE);
		// db.execSQL("DROP TABLE IF EXISTS " + SAVED_STATES_TABLE);
		
		// Then we run the onCreate() method again //
		onCreate(db);
		
		// close the database connection
		db.close();
	}
}