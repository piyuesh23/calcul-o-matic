package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ParticipantsDataSource {
	private SQLiteDatabase database;
	private SqliteAdapter dbHelper;
	private String[] allColumns = { SqliteAdapter.COLUMN_UID,
			SqliteAdapter.COLUMN_EID, SqliteAdapter.COLUMN_AMOUNT};
	public static final String LOG_TAG = "Calculomatic";
	public ParticipantsDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Long addParticipant(Long uid, String eid, String amount) {
		ContentValues values = new ContentValues();		
		values.put(SqliteAdapter.COLUMN_UID, uid);
		values.put(SqliteAdapter.COLUMN_EID, Long.parseLong(eid));
		values.put(SqliteAdapter.COLUMN_AMOUNT, Integer.parseInt(amount));
		long insertId = database.insert(SqliteAdapter.TABLE_PARTICIPANTS, null,
				values);		
		return insertId;
	}
	
	public List<Participant> participantsForEvent(String eid) {
		List<Participant> participants = new ArrayList<Participant>();
		Long eventid = Long.parseLong(eid);
		Cursor cursor = database.query(SqliteAdapter.TABLE_PARTICIPANTS,
				allColumns, SqliteAdapter.COLUMN_EID + "=" + eventid, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Participant participant = cursorToParticipant(cursor);
			participants.add(participant);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return participants;
	}
	
	private Participant cursorToParticipant(Cursor cursor) {
		Participant participant= new Participant();
		participant.setuid(cursor.getLong(0));
		participant.setEid(cursor.getLong(1));
		participant.setamount(cursor.getInt(2));		
		return participant;
	}
}