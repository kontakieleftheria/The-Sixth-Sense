package com.kontakipapadogiannis.thesixthsense;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

@SuppressLint("InlinedApi")
public class ResultsActivity extends Activity {

	String data = "";
	TableLayout tl;
	TableRow tr;
	TextView result_time;
	TextView humid;
	TextView humtemp;
	TextView adc0;
	TextView digi0;
	ScrollView scroll;
	Thread thread;
	GetDataFromDB getdb;
	ArrayList<Date> date;
	ArrayList<Double> humidity;
	ArrayList<Double> temperature;
	ArrayList<Double> adc;
	ArrayList<Double> digi;
	int day;
	int hour;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scroll = (ScrollView) findViewById(R.id.scroll);
		tl = (TableLayout) findViewById(R.id.maintable);

		getdb = new GetDataFromDB();
		date = new ArrayList<Date>();
		humidity = new ArrayList<Double>();
		temperature = new ArrayList<Double>();
		adc = new ArrayList<Double>();
		digi = new ArrayList<Double>();

		day = getIntent().getExtras().getInt("dayOfMonth");
		hour = getIntent().getExtras().getInt("hourOfDay");

		thread = new Thread(new Runnable() {
			public void run() {
				// getdb = new GetDataFromDB();

				data = getdb.getDataFromDB(day, hour);

				// System.out.println(data);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// date = new ArrayList<Date>();
						// humidity = new ArrayList<Double>();
						// temperature = new ArrayList<Double>();
						// adc = new ArrayList<Double>();
						// digi = new ArrayList<Double>();

						ParseJSON parseJSON = new ParseJSON();
						try {
							ParseJSON.parse(data);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						date = parseJSON.getDate();
						humidity = parseJSON.getHumid();
						temperature = parseJSON.getHumtemp();
						adc = parseJSON.getAdc0();
						digi = parseJSON.getDigi0();

						addHeader();
						addData(date, humidity, temperature, adc, digi);

					}
				});
			}
		});

		thread.start();

	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu:
			finish();
			Intent intent = new Intent(getApplicationContext(), Menu.class);
			startActivity(intent);

			return true;
		case R.id.refresh:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc.clear();
			digi.clear();
			finish();
			Intent intent1 = new Intent(this, ResultsActivity.class);
			intent1.putExtra("dayOfMonth",
					getIntent().getExtras().getInt("dayOfMonth"));
			intent1.putExtra("hourOfDay",
					getIntent().getExtras().getInt("hourOfDay"));
			intent1.putExtra("whichGraph",
					getIntent().getExtras().getString("whichGraph"));

			startActivity(intent1);
			return true;
		case R.id.last24Hours:
			finish();
			Intent intent2 = new Intent(this, ResultsActivity.class);
			intent2.putExtra("dayOfMonth", 0);
			intent2.putExtra("hourOfDay", 24);
			startActivity(intent2);
			return true;
		case R.id.lastTwoDays:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc.clear();
			digi.clear();
			finish();
			Intent intent3 = new Intent(this, ResultsActivity.class);
			intent3.putExtra("dayOfMonth", 2);
			intent3.putExtra("hourOfDay", 0);
			startActivity(intent3);
			return true;
		case R.id.lastWeek:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc.clear();
			digi.clear();
			finish();
			Intent intent4 = new Intent(this, ResultsActivity.class);
			intent4.putExtra("dayOfMonth", 7);
			intent4.putExtra("hourOfDay", 0);
			startActivity(intent4);
			return true;
		case R.id.lastMonth:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc.clear();
			digi.clear();
			finish();
			Intent intent5 = new Intent(this, ResultsActivity.class);
			intent5.putExtra("dayOfMonth", 30);
			intent5.putExtra("hourOfDay", 24);
			startActivity(intent5);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	void addHeader() {
		/** Create a TableRow dynamically **/
		tr = new TableRow(this);
		LinearLayout.LayoutParams params;
		LinearLayout Ll;

		/** Creating a TextView to add to the row **/
		result_time = new TextView(this);
		result_time.setText("Result Time");
		result_time.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		result_time.setPadding(2, 2, 2, 2);
		result_time.setBackgroundColor(Color.RED);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 2, 2, 2);
		Ll.addView(result_time, params);
		tr.addView((View) Ll); // Adding textView to tablerow.

		/** Creating Qty Button **/
		humid = new TextView(this);
		humid.setText("Humidity");
		humid.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		humid.setPadding(2, 2, 2, 2);
		humid.setBackgroundColor(Color.RED);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 2, 2, 2);
		Ll.addView(humid, params);
		tr.addView((View) Ll); // Adding textview to tablerow.

		humtemp = new TextView(this);
		humtemp.setText("Temperature");
		humtemp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		humtemp.setPadding(2, 2, 2, 2);
		humtemp.setBackgroundColor(Color.RED);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 2, 2, 2);
		Ll.addView(humtemp, params);
		tr.addView((View) Ll); // Adding textview to tablerow.

		adc0 = new TextView(this);
		adc0.setText("adc0");
		adc0.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		adc0.setPadding(2, 2, 2, 2);
		adc0.setBackgroundColor(Color.RED);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 2, 2, 2);
		Ll.addView(adc0, params);
		tr.addView((View) Ll); // Adding textview to tablerow.

		digi0 = new TextView(this);
		digi0.setText("digi0");
		digi0.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		digi0.setPadding(2, 2, 2, 2);
		digi0.setBackgroundColor(Color.RED);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 2, 2, 2);
		Ll.addView(digi0, params);
		tr.addView((View) Ll); // Adding textview to tablerow.

		// Add the TableRow to the TableLayout
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	public void addData(ArrayList<Date> dates, ArrayList<Double> ygrasia,
			ArrayList<Double> thermokrasia, ArrayList<Double> adc,
			ArrayList<Double> digi) {

		for (int i = 0; i < dates.size(); i++) {

			// Users p = (Users) i.next();

			// ParseJSON p = (ParseJSON) i.next();

			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			LinearLayout.LayoutParams params;
			LinearLayout Ll;

			/** Creating a TextView to add to the row **/
			result_time = new TextView(this);
			result_time.setText((dates.get(i)).toString());
			result_time.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			result_time.setPadding(2, 2, 2, 2);
			result_time.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			Ll.addView(result_time, params);
			tr.addView((View) Ll); // Adding textView to tablerow.

			/** Creating Qty Button **/
			humid = new TextView(this);
			humid.setText(Double.toString(humidity.get(i)) + "%");
			humid.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			humid.setPadding(2, 2, 2, 2);
			humid.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			Ll.addView(humid, params);
			tr.addView((View) Ll); // Adding textview to tablerow.

			humtemp = new TextView(this);
			humtemp.setText(Double.toString(temperature.get(i)));
			humtemp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			humtemp.setPadding(2, 2, 2, 2);
			humtemp.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			Ll.addView(humtemp, params);
			tr.addView((View) Ll); // Adding textview to tablerow.

			adc0 = new TextView(this);
			adc0.setText(Double.toString(adc.get(i)));
			adc0.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			adc0.setPadding(2, 2, 2, 2);
			adc0.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			Ll.addView(adc0, params);
			tr.addView((View) Ll); // Adding textview to tablerow.

			digi0 = new TextView(this);
			digi0.setText(Double.toString(digi.get(i)));
			digi0.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			digi0.setPadding(2, 2, 2, 2);
			digi0.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			Ll.addView(digi0, params);
			tr.addView((View) Ll); // Adding textview to tablerow.

			// Add the TableRow to the TableLayout
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			// scroll.post(new Runnable() {
			// @Override
			// public void run() {
			// scroll.fullScroll(View.FOCUS_UP);
			// }
			// });
		}
	}
}
