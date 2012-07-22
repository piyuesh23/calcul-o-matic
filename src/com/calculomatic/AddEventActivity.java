package com.calculomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEventActivity extends Activity {
	private EventsDataSource datasource;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);		
	}
	
	public void sendMessage(View view) {
		EditText event_name = (EditText)findViewById(R.id.event_name);
		EditText event_place = (EditText)findViewById(R.id.event_place);
		datasource = new EventsDataSource(this);
		datasource.open();
		datasource.createEvent(event_name.getText().toString(), event_place.getText().toString());
		Intent intent = new Intent(this, AddContributorActivity.class);
		startActivity(intent);
	}
}
