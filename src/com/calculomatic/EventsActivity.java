package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventsActivity extends Activity {	
	private EventsDataSource datasource;
	private ParticipantsDataSource participantsdatasource;
	private ContributorsDataSource contributorsdatasource;
	public List<Event> events = new ArrayList<Event>();
	public List<Long> eventid = new ArrayList<Long>();	
	public static final String LOG_TAG = "Calculomatic";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		ListView eventsList = (ListView)findViewById(R.id.listView1);
		datasource = new EventsDataSource(this);
		datasource.open();		
		events = datasource.getAllEvents();
		datasource.close();
		for (Event e : events) {
			eventid.add(e.getId());
		}
		final ArrayAdapter<Event> arrayAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);
		eventsList.setAdapter(arrayAdapter);
		registerForContextMenu(eventsList);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
	      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	      Long eid = eventid.get((int)info.id);
	      switch(item.getItemId()) {
	      case R.id.edit:	    	  
	    	  Intent intent = new Intent(this, EditEventActivity.class);
	    	  intent.putExtra("event", eid);
	    	  startActivity(intent);
	    	  return true;	  
	      case R.id.delete:
	    	  participantsdatasource = new ParticipantsDataSource(this);
	    	  participantsdatasource.open();			    	  
	    	  participantsdatasource.deleteParticipantsForEvent(eid);
	    	  participantsdatasource.close();
	    	  contributorsdatasource = new ContributorsDataSource(this);
	    	  contributorsdatasource.open();			    	  
	    	  contributorsdatasource.deleteContributorsForEvent(eid);
	    	  contributorsdatasource.close();
	    	  datasource = new EventsDataSource(this);
	    	  datasource.open();			    	  
	    	  datasource.deleteEvent(eid);
	    	  datasource.close();
	    	  ListView eventsList = (ListView)findViewById(R.id.listView1);
	    	  datasource = new EventsDataSource(this);
	    	  datasource.open();		
	    	  events = datasource.getAllEvents();
	    	  datasource.close();
	    	  for (Event e : events) {
	    		  eventid.add(e.getId());
	    	  }
	    	  final ArrayAdapter<Event> arrayAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);
	    	  eventsList.setAdapter(arrayAdapter);
	    	  return true;	 
	      default:
	    	  return super.onContextItemSelected(item);
	      }
	}

}
