package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class UsersActivity extends Activity{
	private UsersDataSource usersdatasource;
	public List<User> users = new ArrayList<User>();
	public List<Long> uid = new ArrayList<Long>();
	public static final String LOG_TAG = "Calculomatic";
	public ListView usersList;
	private ContributorsDataSource contributorsdatasource;
	private ParticipantsDataSource participantsdatasource;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		usersdatasource = new UsersDataSource(this);
		usersdatasource.open();
		users = usersdatasource.getAllUsers();
		usersdatasource.close();		
		
		for(User u : users) {
			uid.add(u.getId());
		}
		usersList = new ListView(this);
		final ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
		Log.v(LOG_TAG, arrayAdapter.getCount()+"");
		usersList.setAdapter(arrayAdapter);
		registerForContextMenu(usersList);
		usersList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);					
					Long uid= arrayAdapter.getItem(position).getId();
					intent.putExtra("uid", uid);
					startActivity(intent);					
			}
		});

		setContentView(usersList);
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
	      Long userid = uid.get((int)info.id);
	      switch(item.getItemId()) {
		      case R.id.edit:	    	  
		    	  Intent intent = new Intent(this, EditUserActivity.class);
		    	  intent.putExtra("uid", userid);
		    	  startActivity(intent);
		    	  return true;	  
		      case R.id.delete:
		    	  usersdatasource = new UsersDataSource(this);
		    	  usersdatasource.open();
		    	  usersdatasource.deleteUser(userid);
		    	  users = usersdatasource.getAllUsers();
		    	  usersdatasource.close();
		    	  contributorsdatasource = new ContributorsDataSource(this);
		    	  contributorsdatasource.open();
		    	  contributorsdatasource.deleteContributors(userid);
		    	  contributorsdatasource.close();
		    	  participantsdatasource = new ParticipantsDataSource(this);
		    	  participantsdatasource.open();
		    	  participantsdatasource.deleteParticipants(userid);
		    	  participantsdatasource.close();
		    	  final ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
		    	  usersList.setAdapter(arrayAdapter);
		    	  return true;	 
		      default:
		    	  return super.onContextItemSelected(item);
	      }
	}
}
