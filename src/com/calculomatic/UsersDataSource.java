package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class UsersDataSource {
	private SQLiteDatabase database;
	private SqliteAdapter dbHelper;
	private String[] allColumns = { SqliteAdapter.COLUMN_USER_ID,
			SqliteAdapter.COLUMN_USERNAME, SqliteAdapter.COLUMN_PASSWORD, SqliteAdapter.COLUMN_EMAIL};

	public UsersDataSource(Context context) {
		dbHelper = new SqliteAdapter(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public User createUser(String username, String password, String email) {
		ContentValues values = new ContentValues();
		values.put(SqliteAdapter.COLUMN_USERNAME, username);
		values.put(SqliteAdapter.COLUMN_PASSWORD, password);
		values.put(SqliteAdapter.COLUMN_EMAIL, email);
		long insertId = database.insert(SqliteAdapter.TABLE_USERS, null,
				values);
		Cursor cursor = database.query(SqliteAdapter.TABLE_USERS,
				allColumns, SqliteAdapter.COLUMN_USER_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		User newUser = cursorToEvent(cursor);
		cursor.close();
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
	private User cursorToEvent(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		user.setEmail(cursor.getString(3));
		return user;
	}
}
