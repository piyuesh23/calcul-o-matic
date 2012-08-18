package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class ExpenseActivity extends Activity {
	private ContributorsDataSource contributorsdatasource;
	private EventsDataSource eventsdatasource;
	private ParticipantsDataSource participantsdatasource;
	public static String uid;
	public List <Contributor> contributors= new ArrayList<Contributor>();
	public List <Participant> participants= new ArrayList<Participant>();
	public Integer total_spent = 0;
	public Integer total_due = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		ScrollView sv = new ScrollView(this);
		TableLayout tl = new TableLayout(this);
		tl.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		Intent incomingintent = getIntent();
		Bundle b = incomingintent.getExtras();
		uid = b.get("uid").toString();
		
		participantsdatasource = new ParticipantsDataSource(this);
		participantsdatasource.open();
		participants = participantsdatasource.getEventForUser(uid);
		participantsdatasource.close();
		TableRow thr=new TableRow(this);
		TextView th1 = new TextView(this);
		th1.setTextAppearance(this, android.R.style.TextAppearance_Large);
		th1.setText("Event");
		th1.setPadding(10, 10, 10, 10);
		TextView th2 = new TextView(this);
		th2.setTextAppearance(this, android.R.style.TextAppearance_Large);
		th2.setText("Spent");
		th2.setPadding(10, 10, 10, 10);
		TextView th3 = new TextView(this);
		th3.setTextAppearance(this, android.R.style.TextAppearance_Large);
		th3.setText("Dues");
		th3.setPadding(10, 10, 10, 10);
		thr.addView(th1);
		thr.addView(th2);
		thr.addView(th3);
		thr.setPadding(10, 10, 10, 10);
		tl.addView(thr);
		for(Participant p : participants) {			
			eventsdatasource = new EventsDataSource(this);
			eventsdatasource.open();
			Event e  = eventsdatasource.getEventById(p.getEid()+"");			
			eventsdatasource.close();	
			
			contributorsdatasource = new ContributorsDataSource(this);
			contributorsdatasource.open();
			Contributor c = contributorsdatasource.getContributorForEvent(p.getEid()+"", p.getuid()+"");
			contributorsdatasource.close();
			participantsdatasource = new ParticipantsDataSource(this);
			participantsdatasource.open();
			Participant p1 = participantsdatasource.getParticipantForEvent(p.getEid()+"", uid);
			participantsdatasource.close();
			
			TableRow tr=new TableRow(this);
			TextView t1 = new TextView(this);
			t1.setTextAppearance(this, android.R.style.TextAppearance_Large);
			t1.setText(e.getEvent());
			t1.setPadding(10, 10, 10, 10);
			TextView t2 = new TextView(this);
			t2.setTextAppearance(this, android.R.style.TextAppearance_Large);
			t2.setText(c.getamount().toString());
			t2.setPadding(10, 10, 10, 10);
			TextView t3 = new TextView(this);
			t3.setTextAppearance(this, android.R.style.TextAppearance_Large);			
			t3.setText(p1.getamount().toString());
			t3.setPadding(10, 10, 10, 10);
			tr.addView(t1);         	
			tr.addView(t2);
			tr.addView(t3);
			tr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tr.setPadding(10, 10, 10, 10);
			total_spent += c.getamount();
			total_due += p1.getamount();
			tl.addView(tr); 			
		}				 
		
		TableRow tfr=new TableRow(this);
		TextView tf1 = new TextView(this);
		tf1.setTextAppearance(this, android.R.style.TextAppearance_Large);
		tf1.setText("Total");
		tf1.setPadding(10, 10, 10, 10);
		TextView tf2 = new TextView(this);
		tf2.setTextAppearance(this, android.R.style.TextAppearance_Large);
		tf2.setPadding(10, 10, 10, 10);
		tf2.setText(total_spent.toString());
		TextView tf3 = new TextView(this);
		tf3.setTextAppearance(this, android.R.style.TextAppearance_Large);
		tf3.setPadding(10, 10, 10, 10);
		tf3.setText(total_due.toString());
		tfr.addView(tf1);
		tfr.addView(tf2);
		tfr.addView(tf3);
		tl.addView(tfr);
		tfr.setPadding(10, 10, 10, 10);
		sv.addView(tl);
		setContentView(sv);
	}
}
