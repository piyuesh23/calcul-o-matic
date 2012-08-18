package com.calculomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		TextView fullname = (TextView)findViewById(R.id.fullname_value);
		TextView email = (TextView)findViewById(R.id.email_value);
		
		datasource = new UsersDataSource(this);
		datasource.open();
		User u = datasource.getUser(uid);
		datasource.close();
		username.setText(u.getUsername());
		fullname.setText(u.getFullname());
		email.setText(u.getEmail());		
	}
	
	public void listEvents(View v) {
		Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
		startActivity(intent);
	}
	
	public void listExpenses(View v) {
		Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
		intent.putExtra("uid", uid);
		startActivity(intent);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
		int userid = Integer.parseInt(uid);
		if(userid == 1) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.settings_admin, menu);        
	        return super.onCreateOptionsMenu(menu);
		}
		else {
			MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.settings_authenticated, menu);        
	        return super.onCreateOptionsMenu(menu);
		}
    }



	@Override
    public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
        case R.id.eventsList:
        	Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
    		intent.putExtra("uid", uid);
    		startActivity(intent);
            break;        
        case R.id.usersList:
        	Intent intent2 = new Intent(getApplicationContext(), UsersActivity.class);
    		intent2.putExtra("uid", uid);
    		startActivity(intent2);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
