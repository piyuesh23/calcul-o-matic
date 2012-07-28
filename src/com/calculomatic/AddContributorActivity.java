package com.calculomatic;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class AddContributorActivity extends ListActivity {
	private UsersDataSource datasource;
	public List<User> users = new ArrayList<User>();
	public List<String> Users = new ArrayList<String>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);						
		
		datasource = new UsersDataSource(this);
		datasource.open();
		users = datasource.getAllUsers();
		for(User u : users) {		
			Users.add(u.getUsername().toString());
		}
		setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, Users));
		final ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setContentView(R.layout.activity_contributor);
	}
}
