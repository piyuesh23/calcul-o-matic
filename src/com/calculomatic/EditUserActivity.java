package com.calculomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserActivity extends Activity {
	private UsersDataSource usersdatasource;
	public Long uid;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView sv =new ScrollView(this);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();		
		final Long uid = Long.parseLong(b.get("uid").toString());			
		
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(1);
		layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		usersdatasource = new UsersDataSource(this);
		usersdatasource.open();
		User u = usersdatasource.getUserFromUid(uid);
		usersdatasource.close();
		
		TextView username_label = new TextView(this);
		username_label.setText("Username");
		layout.addView(username_label);
		
		EditText username = new EditText(this);
		username.setText(u.getUsername().toString());
		username.setId(1);
		layout.addView(username);
		
		TextView fullname_label = new TextView(this);
		fullname_label.setText("fullname");
		layout.addView(fullname_label);
		
		EditText fullname = new EditText(this);
		fullname.setText(u.getFullname().toString());
		fullname.setId(2);
		layout.addView(fullname);
		
		TextView email_label = new TextView(this);
		email_label.setText("email");
		layout.addView(email_label);
		
		EditText email = new EditText(this);
		email.setText(u.getEmail().toString());
		email.setId(3);
		layout.addView(email);
		
		TextView password_label = new TextView(this);
		password_label.setText("Password");
		layout.addView(password_label);
		
		EditText password = new EditText(this);
		password.setText(u.getPassword());
		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		password.setId(4);
		layout.addView(password);

		TextView confirm_password_label = new TextView(this);
		confirm_password_label.setText("Confirm Password");		
		layout.addView(confirm_password_label);
		
		EditText confirm_password = new EditText(this);
		confirm_password.setText(u.getPassword());
		confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		confirm_password.setId(5);
		layout.addView(confirm_password);
		
		Button save = new Button(this);
		save.setText("save");
		layout.addView(save);
		
		sv.addView(layout);
		setContentView(sv);
		
		save.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	User u = new User();
            	Integer profileCount = layout.getChildCount();
            	EditText username = (EditText) findViewById(1);            	
            	EditText fullname = (EditText) findViewById(2);
            	EditText email = (EditText) findViewById(3);
            	EditText password = (EditText) findViewById(4);
            	EditText confirm_password = (EditText) findViewById(5);
            	String result = validateProfile(username.getText().toString(), fullname.getText().toString(), email.getText().toString(), password.getText().toString(), confirm_password.getText().toString());
            	if(result.equals("success")) {
	            	u.setUsername(username.getText().toString());
	            	u.setFullname(fullname.getText().toString());
	            	u.setEmail(email.getText().toString());
	            	u.setPassword(password.getText().toString());
	            	u.setId(uid);
	            	usersdatasource = new UsersDataSource(getApplicationContext());
	            	usersdatasource.open();
	            	int row_count = usersdatasource.updateUser(u);
	            	usersdatasource.close();
	            	if(row_count == 1) {
	            		Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
	            		startActivity(intent);
	            	}
	            	else {
	            		Toast.makeText(getApplicationContext(), "Error occurred while updating user", 10).show();
	            	}
            	}
            	else {
            		Toast.makeText(getApplicationContext(), result, 10).show();
            	}
            }
		});
	}
	
	public String validateProfile(String username, String fullname, String email, String password, String confirm_password) {
		if((username == "") || (fullname == "") || (email == "")) {
			return "Empty Fields";
		}
		else if(!(password.equals(confirm_password))) {
			return "Password validation error";
		}
		return "success";
	}
}
