package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	public static String uid;
	private UsersDataSource datasource;
	private EventsDataSource eventsdatasource;
	private ContributorsDataSource contributorsdatasource;
	private ParticipantsDataSource participantsdatasource;
	public List<User> users = new ArrayList<User>();
	public List<String> contacts = new ArrayList<String>();	
	public List<Contributor> contributors = new ArrayList<Contributor>();
	public List<Participant> participants = new ArrayList<Participant>();
	public static final String LOG_TAG = "Calculomatic";
	public static final String TEST_MESSAGE = "We are trying to add an auto push text feature for this app. So please bear with these test messages for sometime. Test SMS Message -- Calculomatic";
	public static final String OPTIONAL_MESSAGE = "Please acknowledge the reciept.";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);		
	    Intent incomingintent = getIntent();
		Bundle b = incomingintent.getExtras();
		uid = b.get("uid").toString();
		
		TextView username = (TextView)findViewById(R.id.username_value);
		TextView fullname = (TextView)findViewById(R.id.fullname_value);
		TextView email = (TextView)findViewById(R.id.email_value);
		TextView contact = (TextView)findViewById(R.id.contact_value);
		
		datasource = new UsersDataSource(this);
		datasource.open();
		User u = datasource.getUser(uid);
		datasource.close();
		username.setText(u.getUsername());
		fullname.setText(u.getFullname());
		email.setText(u.getEmail());	
		contact.setText(u.getContact());
	}
	
	public void listEvents(View v) {
		Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
		startActivity(intent);
	}
	
	public void listExpenses(View v) {
		Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
		intent.putExtra("uid", uid);
		startActivity(intent);
	}
	
	public void notifyAll(View v) {
		datasource = new UsersDataSource(this);
		datasource.open();
		users = datasource.getAllUsers();
		SmsManager sm = SmsManager.getDefault(); 
		contributorsdatasource = new ContributorsDataSource(this);
		contributorsdatasource.open();
		participantsdatasource = new ParticipantsDataSource(this);
		participantsdatasource.open();
		String message1, message2, message3 = "";
		for(User u: users) {									
			String number = u.getContact().toString();
			Integer contribution = 0;
			Integer expenses = 0;
			Integer balance = 0;
			long uid = u.getId();
			final ArrayList<String> messageParts = new ArrayList<String>();
			contributors = contributorsdatasource.getEventForUser(uid+"");
			for(Contributor c : contributors) {
				contribution += c.getamount();
			}			
			
			participants = participantsdatasource.getEventForUser(uid+"");
			for(Participant p : participants) {
				expenses += p.getamount();
			}
						
			balance = contribution - expenses;			
			message1 = "Hi " + u.getFullname() + ". Thank you for choosing calcul-o-matic." ;
			if(balance > 0)
				message2 = "You will recieve Rs. " + balance + " as the balance amount.";
			else
				message2 = "You need to pay Rs. " + balance + " as the balance amount.";
			message3 = "Amount you contributed in various events is Rs. " + contribution + ". Total amount you spent is Rs. " + expenses + ".";
			messageParts.add(message1);
			messageParts.add(message2);
			messageParts.add(message3);
			messageParts.add(OPTIONAL_MESSAGE);
			sm.sendMultipartTextMessage(number, null, messageParts, null, null);				
		}
		datasource.close();
		contributorsdatasource.close();
		participantsdatasource.close();
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
		int userid = Integer.parseInt(uid);
		if(userid == 1) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.settings_admin, menu);        
	        return super.onCreateOptionsMenu(menu);
		}
		else {
			MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.settings_authenticated, menu);        
	        return super.onCreateOptionsMenu(menu);
		}
    }



	@Override
    public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
        case R.id.eventsList:
        	Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
    		intent.putExtra("uid", uid);
    		startActivity(intent);
            break;        
        case R.id.usersList:
        	Intent intent2 = new Intent(getApplicationContext(), UsersActivity.class);
    		intent2.putExtra("uid", uid);
    		startActivity(intent2);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
