package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	public List<User> users = new ArrayList<User>();	
	private UsersDataSource datasource;
	public final String LOG_TAG = "Calculomatic";
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);        
	 }	 
	 
	 public void sendMessage(View view) {
		EditText username = (EditText)findViewById(R.id.username);
		EditText password = (EditText)findViewById(R.id.password);
		EditText confirm_password = (EditText)findViewById(R.id.confirm_password);
		EditText email = (EditText)findViewById(R.id.email);
		EditText fullname = (EditText)findViewById(R.id.full_name);
		TextView error = (TextView)findViewById(R.id.error);
		datasource = new UsersDataSource(this);
		datasource.open();				
		
		if(username.getText().toString().equals("")) {
			error.setText("Empty Username");			
		}
		if((password.getText().toString().equals(confirm_password.getText().toString()))) {			
			error.setText("Passwords don't match");
		}		
		users = datasource.getAllUsers();
		for(User u : users) {
			if(u.getEmail().toString().equals(email.getText().toString())) {								
				error.setFocusableInTouchMode(true);
				error.requestFocus();
				error.setTextColor(1);
				error.setText("Email Already Exists");
			}
		}
		
		if(error.getText().equals("")) {
			//do nothing
		}
		else {
			datasource.createUser(username.getText().toString(), fullname.getText().toString(), password.getText().toString(), email.getText().toString());			
			username.setText("");
			password.setText("");
			confirm_password.setText("");			
			email.setText("");
			fullname.setText("");
			error.setFocusableInTouchMode(true);
			error.requestFocus();
			error.setText("User " + username.getText().toString() + "created successfully.");
		}
	 }
}
