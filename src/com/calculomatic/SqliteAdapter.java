package com.calculomatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteAdapter extends SQLiteOpenHelper {
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_EVENT_ID = "event_id";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_EVENT= "event";
	public static final String COLUMN_PLACE= "place";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_EMAIL = "email";
	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_EVENTS_CREATE = "create table "
			+ TABLE_EVENTS+ "(" + COLUMN_EVENT_ID
			+ " integer primary key autoincrement, " + COLUMN_EVENT
			+ " text not null, " + COLUMN_PLACE
			+ " text not null);";
	
	private static final String DATABASE_USERS_CREATE = "create table "
			+ TABLE_USERS+ "(" + COLUMN_USER_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME
			+ " text not null, " + COLUMN_PASSWORD
			+ " text not null, " + COLUMN_EMAIL + " text not null);";
	
	private static final String DATABASE_EVENTS_DROP = "DROP TABLE IF EXISTS " + TABLE_EVENTS;
	private static final String DATABASE_USERS_DROP = "DROP TABLE IF EXISTS " + TABLE_USERS;	

	public SqliteAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
//		database.execSQL(DATABASE_DROP);
		database.execSQL(DATABASE_EVENTS_CREATE);
		database.execSQL(DATABASE_USERS_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SqliteAdapter.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		onCreate(db);
	}
}
