package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventsDataSource {
	private SQLiteDatabase database;
	private SqliteAdapter dbHelper;
	private String[] allColumns = { SqliteAdapter.COLUMN_ID,
			SqliteAdapter.COLUMN_EVENT};

	public EventsDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public Event createEvent(String event) {
		ContentValues values = new ContentValues();
		values.put(SqliteAdapter.COLUMN_EVENT, event);
		long insertId = database.insert(SqliteAdapter.TABLE_EVENTS, null,
				values);
		Cursor cursor = database.query(SqliteAdapter.TABLE_EVENTS,
				allColumns, SqliteAdapter.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Event newEvent = cursorToEvent(cursor);
		cursor.close();
		return newEvent;
	}
	
	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<Event>();

		Cursor cursor = database.query(SqliteAdapter.TABLE_EVENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Event event = cursorToEvent(cursor);
			events.add(event);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return events;
	}
	private Event cursorToEvent(Cursor cursor) {
		Event event = new Event();
		event.setId(cursor.getLong(0));
		event.setComment(cursor.getString(1));
		return event;
	}
}
