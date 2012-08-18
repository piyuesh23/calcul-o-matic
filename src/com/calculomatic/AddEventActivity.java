package com.calculomatic;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddEventActivity extends Activity {
	private EventsDataSource datasource;
	private DatePicker dpResult;	

	private int year;
	private int month;
	private int day;
	private String date;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);		
	}
	
	public void sendMessage(View view) {
		EditText event_name = (EditText)findViewById(R.id.event_name);
		EditText event_place = (EditText)findViewById(R.id.event_place);
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		 
		datasource = new EventsDataSource(this);
		datasource.open();
		day = dpResult.getDayOfMonth();
		month = dpResult.getMonth();
		year = dpResult.getYear();
		
		date = day + "/" + month + "/" + year;
		Event e1 = datasource.createEvent(event_name.getText().toString(), event_place.getText().toString(), date);		
		Intent intent = new Intent(this, EventsActivity.class);		
		startActivity(intent);
	}
}
