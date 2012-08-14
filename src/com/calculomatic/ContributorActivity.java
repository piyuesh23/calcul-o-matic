package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ContributorActivity extends Activity {
	private UsersDataSource datasource;
	private EventsDataSource eventsdatasource;
	private ContributorsDataSource contributorsdatasource;
	private ParticipantsDataSource participantsdatasource;
	public List<User> users = new ArrayList<User>();	
	public List<Contributor> contributors = new ArrayList<Contributor>();
	public List<Participant> participants = new ArrayList<Participant>();
	public List<String> Users = new ArrayList<String>();
	public List<String> Contributors = new ArrayList<String>();
	public List<String> Participants = new ArrayList<String>();
	public static String eventid;
	public static final String LOG_TAG = "Calculomatic";
	public String contrib = "";
	public String participant = "";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_contributor);
		Intent incomingintent = getIntent();
		Bundle b = incomingintent.getExtras();
		
		eventid = b.get("event").toString();

		eventsdatasource = new EventsDataSource(this);
		eventsdatasource.open();
		Event e1 = eventsdatasource.getEventById(eventid);
		eventsdatasource.close();
		
		TextView title = (TextView)findViewById(R.id.Title);
		TextView description = (TextView)findViewById(R.id.Description);
		TextView contrib_text = (TextView)findViewById(R.id.contributor_data);
		TextView participant_text = (TextView)findViewById(R.id.participant_data);
		title.setText(e1.getEvent());
		description.setText(e1.getPlace());
		contributorsdatasource = new ContributorsDataSource(this);
		contributorsdatasource.open();
		contributors = contributorsdatasource.contributorsForEvent(eventid);
		contributorsdatasource.close();
		for(Contributor c : contributors) {
			datasource = new UsersDataSource(this);
			datasource.open();
			User u  = datasource.getUserFromUid(c.getuid());
			datasource.close();
			Contributors.add(u.getUsername().toString() + "----" + c.getamount().toString());
			contrib += u.getUsername().toString() + "----" + c.getamount().toString() + "|";
		}		
		Log.v(LOG_TAG, Contributors.toString());
		contrib_text.setText(contrib);
		
		participantsdatasource = new ParticipantsDataSource(this);
		participantsdatasource.open();
		participants = participantsdatasource.participantsForEvent(eventid);
		participantsdatasource.close();
		for(Participant p : participants) {
			datasource = new UsersDataSource(this);
			datasource.open();
			User u  = datasource.getUserFromUid(p.getuid());
			datasource.close();
			Participants.add(u.getUsername().toString() + "----" + p.getamount().toString());
			participant += u.getUsername().toString() + "----" + p.getamount().toString() + "|";
		}
		participant_text.setText(participant);        
	}
	
	public void sendMessage(View v) {
		Intent intent = new Intent(this, AddContributorActivity.class);
		intent.putExtra("event", eventid);
		startActivity(intent);
	}
	
	public void addParticipant(View v) {
		Intent intent = new Intent(this, AddParticipantActivity.class);
		intent.putExtra("event", eventid);
		startActivity(intent);
	}
}
