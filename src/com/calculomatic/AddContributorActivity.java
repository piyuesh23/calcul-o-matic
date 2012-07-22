package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class AddContributorActivity extends Activity {
	private UsersDataSource datasource;
	public List<User> users = new ArrayList<User>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);				
		TableLayout tl=new TableLayout(this);
		datasource = new UsersDataSource(this);
		datasource.open();
		users = datasource.getAllUsers();
		for(User u : users) {		
			TableRow tr=new TableRow(this);  
			CheckBox chk=new CheckBox(this);  
			chk.setText(u.getUsername().toString());  
			tr.addView(chk);         
			tl.addView(tr);  
		}
		TableRow tr = new TableRow(this);
		Button addContributors = new Button(this);
		addContributors.setText("Add Contributors");
		tr.addView(addContributors);
		tl.addView(tr);
		setContentView(tl); 
	}
}
