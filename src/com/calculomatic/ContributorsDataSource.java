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
		values.put(SqliteAdapter.COLUMN_UID, uid);
		values.put(SqliteAdapter.COLUMN_EID, Long.parseLong(eid));
		values.put(SqliteAdapter.COLUMN_AMOUNT, Integer.parseInt(amount));
		long insertId = database.insert(SqliteAdapter.TABLE_CONTRIBUTORS, null,
				values);		
		return insertId;
	}
	
	public void deleteContributorsForEvent(Long eid) {
		database.delete(SqliteAdapter.TABLE_CONTRIBUTORS, SqliteAdapter.COLUMN_EID + "=" + eid, null);		
	}
	
	public void deleteContributors(Long uid) {
		database.delete(SqliteAdapter.TABLE_CONTRIBUTORS, SqliteAdapter.COLUMN_UID + "=" + uid, null);		
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

	public List<Contributor> getEventForUser(String uid) {
		List<Contributor> contributors = new ArrayList<Contributor>();
		Long userid = Long.parseLong(uid);
		Cursor cursor = database.query(SqliteAdapter.TABLE_CONTRIBUTORS,
				allColumns, SqliteAdapter.COLUMN_UID + "=" + userid, null, null, null, null);

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
	
	public Contributor getContributorForEvent(String eid, String uid) {		
		Long eventid = Long.parseLong(eid);
		Long userid = Long.parseLong(uid);
		Cursor cursor = database.query(SqliteAdapter.TABLE_CONTRIBUTORS,
				allColumns, SqliteAdapter.COLUMN_EID + "=" + eventid + " AND " + SqliteAdapter.COLUMN_UID + "=" + userid, null, null, null, null);		
		if(cursor.moveToFirst()) {
			cursor.moveToFirst();		
			Contributor contributor= cursorToContributor(cursor);
			// Make sure to close the cursor
			cursor.close();		
			return contributor;
		}
		else {
			Contributor contributor = new Contributor();
			contributor.setamount(0);			
			cursor.close();
			return contributor;
		}
	}
	
	public int updateContributor(Contributor c) {
		long eid = c.getEid();
		long uid = c.getuid();
		int amount = c.getamount();		
		Log.v(LOG_TAG, c.getEid()+"");
		Log.v(LOG_TAG, c.getuid()+"");
		Log.v(LOG_TAG, c.getamount()+"");
		ContentValues cv = new ContentValues();
		cv.put(SqliteAdapter.COLUMN_AMOUNT, c.getamount());
		int row_count = database.update(SqliteAdapter.TABLE_CONTRIBUTORS, cv, SqliteAdapter.COLUMN_EID + "=" + c.getEid() + " AND " + SqliteAdapter.COLUMN_UID + "=" + c.getuid(), null);
		Log.v(LOG_TAG, row_count+"");
		return row_count;
	}
}
