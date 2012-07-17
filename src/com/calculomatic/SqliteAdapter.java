package com.calculomatic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteAdapter extends SQLiteOpenHelper {
	public static final String TABLE_EVENTS = "events";
	public static final String COLUMN_ID = "event_id";
	public static final String COLUMN_EVENT= "event";

	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_EVENTS+ "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_EVENT
			+ " text not null);";

	public SqliteAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
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
