package com.calculomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	public static String uid;
	private UsersDataSource datasource;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);		
	    Intent incomingintent = getIntent();
		Bundle b = incomingintent.getExtras();
		uid = b.get("uid").toString();
		
		TextView username = (TextView)findViewById(R.id.username_value);
		TextView email = (TextView)findViewById(R.id.email_value);
		
		datasource = new UsersDataSource(this);
		datasource.open();
		User u = datasource.getUser(uid);
		datasource.close();
		username.setText(u.getUsername());
		email.setText(u.getEmail());		
	}
	
	public void listEvents(View v) {
		Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
		startActivity(intent);
	}
	
	public void listExpenses(View v) {
	
	}
}
