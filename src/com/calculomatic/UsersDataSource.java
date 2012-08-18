package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class UsersDataSource {
	private SQLiteDatabase database;
	public static final String LOG_TAG = "Calculomatic";
	private SqliteAdapter dbHelper;
	private String[] allColumns = { SqliteAdapter.COLUMN_USER_ID, SqliteAdapter.COLUMN_USERNAME, SqliteAdapter.COLUMN_FULLNAME, SqliteAdapter.COLUMN_PASSWORD, SqliteAdapter.COLUMN_EMAIL};

	public UsersDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public User createUser(String username, String fullname, String password, String email) {
		ContentValues values = new ContentValues();
		values.put(SqliteAdapter.COLUMN_USERNAME, username);
		values.put(SqliteAdapter.COLUMN_FULLNAME, fullname);
		values.put(SqliteAdapter.COLUMN_PASSWORD, password);
		values.put(SqliteAdapter.COLUMN_EMAIL, email);
		long insertId = database.insert(SqliteAdapter.TABLE_USERS, null,
				values);
		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, SqliteAdapter.COLUMN_USER_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToEvent(cursor);
		Log.v(LOG_TAG, newUser.getFullname().toString());
		cursor.close();
		close();
		return newUser;
	}
	
	public int updateUser(User u) {
		ContentValues cv = new ContentValues();
		cv.put(SqliteAdapter.COLUMN_USERNAME, u.getUsername());
		cv.put(SqliteAdapter.COLUMN_FULLNAME, u.getFullname());
		cv.put(SqliteAdapter.COLUMN_EMAIL, u.getEmail());
		cv.put(SqliteAdapter.COLUMN_PASSWORD, u.getPassword());
		int row_count = database.update(SqliteAdapter.TABLE_USERS, cv, SqliteAdapter.COLUMN_USER_ID + "=" + u.getId(), null);		
		return row_count;
	}
	
	public void deleteUser(Long uid) {
		database.delete(SqliteAdapter.TABLE_USERS, SqliteAdapter.COLUMN_USER_ID + "=" + uid, null);
	}
	
	public User getUser(String uid) {
		ContentValues values = new ContentValues();
		long userid = Long.parseLong(uid);
		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, SqliteAdapter.COLUMN_USER_ID + " ='" + userid + "'", null, null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToEvent(cursor);
		cursor.close();
		close();
		return newUser;
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToEvent(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return users;
	}
	
	public User getUserFromUsername(String username) {		
		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, SqliteAdapter.COLUMN_USERNAME + " ='" + username + "'", null, null, null, null);

		cursor.moveToFirst();		
		User user = cursorToEvent(cursor);
			
		// Make sure to close the cursor
		cursor.close();
		return user;
	}
	
	public User getUserFromUid(Long uid) {				
		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, SqliteAdapter.COLUMN_USER_ID + " ='" + uid + "'", null, null, null, null);

		cursor.moveToFirst();		
		User user = cursorToEvent(cursor);
			
		// Make sure to close the cursor
		cursor.close();
		return user;
	}
	
	private User cursorToEvent(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setFullname(cursor.getString(2));
		user.setPassword(cursor.getString(3));
		user.setEmail(cursor.getString(4));
		return user;
	}
}
