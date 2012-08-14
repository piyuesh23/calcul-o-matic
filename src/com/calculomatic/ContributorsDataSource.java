package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ContributorsDataSource {
	private SQLiteDatabase database;
	private SqliteAdapter dbHelper;
	private String[] allColumns = { SqliteAdapter.COLUMN_UID,
			SqliteAdapter.COLUMN_EID, SqliteAdapter.COLUMN_AMOUNT};
	public static final String LOG_TAG = "Calculomatic";
	public ContributorsDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Long addContributor(Long uid, String eid, String amount) {
		ContentValues values = new ContentValues();
		Log.v(LOG_TAG, "uid" + uid);
		Log.v(LOG_TAG, "eid" + eid);
		Log.v(LOG_TAG, "amt" + amount);
		values.put(SqliteAdapter.COLUMN_UID, uid);
		values.put(SqliteAdapter.COLUMN_EID, Long.parseLong(eid));
		values.put(SqliteAdapter.COLUMN_AMOUNT, Integer.parseInt(amount));
		long insertId = database.insert(SqliteAdapter.TABLE_CONTRIBUTORS, null,
				values);		
		return insertId;
	}
	
	public List<Contributor> contributorsForEvent(String eid) {
		List<Contributor> contributors = new ArrayList<Contributor>();
		Long eventid = Long.parseLong(eid);
		Cursor cursor = database.query(SqliteAdapter.TABLE_CONTRIBUTORS,
				allColumns, SqliteAdapter.COLUMN_EID + "=" + eventid, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Contributor contributor = cursorToContributor(cursor);
			contributors.add(contributor);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return contributors;
	}
	
	private Contributor cursorToContributor(Cursor cursor) {
		Contributor contributor = new Contributor();
		contributor.setuid(cursor.getLong(0));
		contributor.setEid(cursor.getLong(1));
		contributor.setamount(cursor.getInt(2));		
		return contributor;
	}
}