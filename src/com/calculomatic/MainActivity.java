package com.calculomatic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import org.apache.http.auth.*;

public class MainActivity extends Activity {
	private EventsDataSource datasource;
	public String event = new String("hello");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource = new EventsDataSource(this);
		datasource.open();
		datasource.createEvent(event);
		System.out.print(datasource.getAllEvents());
		Button login = (Button)findViewById(R.id.button1);
		login.setOnClickListener(loginListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    private OnClickListener loginListener = new OnClickListener() {
	    public void onClick(View v) {
			TextView username = (TextView)findViewById(R.id.textView1);
		    username.setText("hello world");
		}
	};
    
}
