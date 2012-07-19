package com.calculomatic;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
	private EventsDataSource datasource;
	public Authentication auth;
	public String event = new String("even1");
	public List l1;
	public static final String LOG_TAG = "Calculomatic";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datasource = new EventsDataSource(this);
		datasource.open();
		datasource.createEvent(event);
		l1 = datasource.getAllEvents();					
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    
	    	
	public void sendMessage(View view) {
	   	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
	}	
	
	public void Authenticate(View view) {
		EditText username = (EditText)findViewById(R.id.editText2);
		EditText password = (EditText)findViewById(R.id.editText3);
		if((username.getText().toString().equals("piyuesh"))&&(password.getText().toString().equals("admin"))) {
		   	Intent intent = new Intent(this, EventsActivity.class);
	    	startActivity(intent);
		}
		else {
			TextView error = (TextView)findViewById(R.id.error);
			error.setText("Invalid Credentials");
		}
	}
	
}