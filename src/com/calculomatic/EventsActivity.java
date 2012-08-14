package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventsActivity extends Activity {	
	private EventsDataSource datasource;	
	public List<Event> events = new ArrayList<Event>();	
	public static final String LOG_TAG = "Calculomatic";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		ListView eventsList = (ListView)findViewById(R.id.listView1);
		datasource = new EventsDataSource(this);
		datasource.open();		
		events = datasource.getAllEvents();
		
		final ArrayAdapter<Event> arrayAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);
		eventsList.setAdapter(arrayAdapter);
				
		eventsList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent intent = new Intent(getApplicationContext(), ContributorActivity.class);					
					Long eid= arrayAdapter.getItem(position).getId();
					intent.putExtra("event", eid);
					startActivity(intent);					
			}
		});
	}
	
	public void sendMessage(View view) {
		Intent intent = new Intent(this, AddEventActivity.class);
		startActivity(intent);
	}

}
