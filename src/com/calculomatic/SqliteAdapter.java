package com.calculomatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteAdapter extends SQLiteOpenHelper {
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_CONTRIBUTORS = "contributors";
	public static final String TABLE_PARTICIPANTS = "participants";
	public static final String COLUMN_EVENT_ID = "event_id";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_EVENT= "event";
	public static final String COLUMN_PLACE= "place";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_FULLNAME = "fullname";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_UID = "uid";
	public static final String COLUMN_EID = "eid";
	public static final String COLUMN_CONTACT = "contact";
	public static final String COLUMN_AMOUNT = "amount";
	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 11;

	// Database creation sql statement
	private static final String DATABASE_EVENTS_CREATE = "create table IF NOT EXISTS "
			+ TABLE_EVENTS+ "(" + COLUMN_EVENT_ID
			+ " integer primary key autoincrement, " + COLUMN_EVENT
			+ " text not null, " + COLUMN_PLACE
			+ " text not null, " + COLUMN_DATE
			+ " text not null);";
	
	private static final String DATABASE_USERS_CREATE = "create table IF NOT EXISTS "
			+ TABLE_USERS+ "(" + COLUMN_USER_ID
			+ " integer primary key autoincrement, " + COLUMN_USERNAME
			+ " text not null, " + COLUMN_PASSWORD
			+ " text not null, " + COLUMN_EMAIL 
			+ " text not null, " + COLUMN_FULLNAME 
			+ " text not null);";
	
	private static final String DATABASE_CONTRIBUTORS_CREATE = "create table IF NOT EXISTS "
			+ TABLE_CONTRIBUTORS + "(" + COLUMN_UID
			+ " integer , " + COLUMN_EID
			+ " integer not null, " + COLUMN_AMOUNT		
			+ " integer not null, " + "foreign key (" + COLUMN_UID + ") references " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
			+ "foreign key (" + COLUMN_EID + ") references " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + ")"
			+ "primary key(" + COLUMN_UID + "," + COLUMN_EID + "));";
	
	private static final String DATABASE_PARTICIPANTS_CREATE = "create table IF NOT EXISTS "
			+ TABLE_PARTICIPANTS + "(" + COLUMN_UID
			+ " integer , " + COLUMN_EID
			+ " integer not null, " + COLUMN_AMOUNT		
			+ " integer not null, " + "foreign key (" + COLUMN_UID + ") references " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
			+ "foreign key (" + COLUMN_EID + ") references " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + ")"
			+ "primary key(" + COLUMN_UID + "," + COLUMN_EID + "));";

	
	private static final String DATABASE_EVENTS_DROP = "DROP TABLE IF EXISTS " + TABLE_EVENTS;
	private static final String DATABASE_USERS_DROP = "DROP TABLE IF EXISTS " + TABLE_USERS;
	private static final String DATABASE_CONTRIBUTORS_DROP = "DROP TABLE IF EXISTS " + TABLE_CONTRIBUTORS;
	private static final String DATABASE_PARTICIPANTS_DROP = "DROP TABLE IF EXISTS " + TABLE_PARTICIPANTS;	
	private static final String USERS_UPGRADE_1 = "ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_CONTACT + " text";

	public SqliteAdapter(Context context) {
		super(context, DATABASE_NAME, null, 11);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
//		database.execSQL(DATABASE_DROP);
		database.execSQL(DATABASE_EVENTS_CREATE);
		database.execSQL(DATABASE_USERS_CREATE);
		database.execSQL(DATABASE_CONTRIBUTORS_CREATE);
		database.execSQL(DATABASE_PARTICIPANTS_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SqliteAdapter.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL(USERS_UPGRADE_1);
		onCreate(db);
	}
}
