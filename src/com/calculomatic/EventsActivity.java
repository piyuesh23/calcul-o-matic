package com.calculomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EventsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
	}
	
	public void sendMessage(View view) {
		Intent intent = new Intent(this, AddEventActivity.class);
		startActivity(intent);
	}
}
