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
	private String[] allColumns = { SqliteAdapter.COLUMN_EVENT_ID,
			SqliteAdapter.COLUMN_EVENT, SqliteAdapter.COLUMN_PLACE};

	public EventsDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public Event createEvent(String event, String place) {
		ContentValues values = new ContentValues();
		values.put(SqliteAdapter.COLUMN_EVENT, event);
		values.put(SqliteAdapter.COLUMN_PLACE, place);
		long insertId = database.insert(SqliteAdapter.TABLE_EVENTS, null,
				values);
		Cursor cursor = database.query(SqliteAdapter.TABLE_EVENTS,
				allColumns, SqliteAdapter.COLUMN_EVENT_ID + " = " + insertId, null,
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
	
	public Event getEventById(String id) {		
		Event e1 = new Event();
		Cursor cursor = database.query(SqliteAdapter.TABLE_EVENTS,
				allColumns, SqliteAdapter.COLUMN_EVENT_ID + " = " + Long.parseLong(id), null, null, null, null);

		cursor.moveToFirst();		
		Event event = cursorToEvent(cursor);
		// Make sure to close the cursor
		cursor.close();
		return event;
	}
	private Event cursorToEvent(Cursor cursor) {
		Event event = new Event();
		event.setId(cursor.getLong(0));
		event.setEvent(cursor.getString(1));
		event.setPlace(cursor.getString(2));
		return event;
	}
	
	public void deleteEvent(Long eid) {
		database.delete(SqliteAdapter.TABLE_EVENTS, SqliteAdapter.COLUMN_EVENT_ID + "=" + eid, null);		
	}
}
