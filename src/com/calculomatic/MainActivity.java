package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
	private UsersDataSource datasource;
	List<User> users = new ArrayList<User>();
	public static final String LOG_TAG = "Calculomatic";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        				
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    
	    	
	public void sendMessage(View view) {
	   	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
	}	
	
	public void Authenticate(View view) {
		datasource = new UsersDataSource(this);
		datasource.open();	
		users = datasource.getAllUsers();
		EditText username = (EditText)findViewById(R.id.editText2);
		EditText password = (EditText)findViewById(R.id.editText3);
		
		for(User u : users) {
			if((username.getText().toString().equals(u.getUsername().toString()))&&(password.getText().toString().equals(u.getPassword().toString()))) {
			   	Intent intent = new Intent(this, EventsActivity.class);
		    	startActivity(intent);
			}
		}
		
		TextView error = (TextView)findViewById(R.id.error);
		error.setText("Invalid Credentials");
	
	}
	
}