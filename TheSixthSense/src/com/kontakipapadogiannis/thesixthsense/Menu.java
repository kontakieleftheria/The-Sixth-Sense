package com.kontakipapadogiannis.thesixthsense;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String MenuItems[] = { "ResultsActivity", "GraphActivity", "GraphActivity",
			"GraphActivity", "GraphActivity" };
	String ItemsNames[] = { "Results", "Humidity Chart", "Temperature Chart",
			"ADC0 Chart (Temperature)", "DIGI0 Chart (Motion)" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this,
				android.R.layout.simple_list_item_1, ItemsNames));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		String str = MenuItems[position];
		String items = ItemsNames[position];
		super.onListItemClick(l, v, position, id);
		try {
			Class<?> myClass = Class
					.forName("com.kontakipapadogiannis.thesixthsense." + str);
			Intent myIntent = new Intent(Menu.this, myClass);
			myIntent.putExtra("dayOfMonth", 0);
			myIntent.putExtra("hourOfDay", 24);
			myIntent.putExtra("whichGraph", items);
			startActivity(myIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}