package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContributorActivity extends Activity {	
	private UsersDataSource datasource;
	private ContributorsDataSource contributordatasource;
	public List<String> Users = new ArrayList<String>();
	public List<User> users = new ArrayList<User>();
	public static String eventid;

    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contributor);		
	    Intent incomingintent = getIntent();
		Bundle b = incomingintent.getExtras();
		Spinner spinner = (Spinner) findViewById(R.id.spinner1); 
	    EditText amount = (EditText) findViewById(R.id.amount);
		eventid = b.get("event").toString();
	    datasource = new UsersDataSource(this);
		datasource.open();
		users = datasource.getAllUsers();
		datasource.close();
		for(User u : users) {		
			Users.add(u.getUsername().toString());
		}
		
	    final ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Users); 
        adapterForSpinner.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item); 
        
        spinner.setAdapter(adapterForSpinner);        
	}
	
	public void sendMessage(View v) {		
		Intent intent = new Intent(this, ContributorActivity.class);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1); 
	    EditText amount = (EditText) findViewById(R.id.amount);
		String username = spinner.getSelectedItem().toString();
		datasource = new UsersDataSource(this);
		datasource.open();
		User u = datasource.getUserFromUsername(username);
		datasource.close();
		contributordatasource = new ContributorsDataSource(this);
		contributordatasource.open();
		Long uid = contributordatasource.addContributor(u.getId(), eventid.toString(), amount.getText().toString());		
		contributordatasource.close();
		intent.putExtra("event", eventid);
		if(uid == -1) {
			Toast.makeText(getApplicationContext(), "add contributor failed", 10).show();
		}
		else {
			startActivity(intent);
		}
	}
}
