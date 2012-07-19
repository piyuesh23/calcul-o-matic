package com.calculomatic;

import android.util.Log;

public class Authentication {
	public static final String LOG_TAG = "Calculomatic";
	public boolean authenticate(String username, String password) {
		Log.v(LOG_TAG, password);
		if((username.equalsIgnoreCase("piyuesh")) && (password.equals("admin")))
			return true;
		else
			return false;
	}
}
