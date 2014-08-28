package com.kontakipapadogiannis.thesixthsense;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class GraphActivity extends Activity {

	String data = "";
	private static Thread thread;
	GetDataFromDB getdb = new GetDataFromDB();
	ArrayList<Date> date = new ArrayList<Date>();
	ArrayList<Double> humidity = new ArrayList<Double>();
	ArrayList<Double> temperature = new ArrayList<Double>();
	ArrayList<Double> adc0 = new ArrayList<Double>();
	ArrayList<Double> digi0 = new ArrayList<Double>();
	int day;
	int hour;

	GraphicalView gView;
	String whichGraph;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		day = getIntent().getExtras().getInt("dayOfMonth");
		hour = getIntent().getExtras().getInt("hourOfDay");

		thread = new Thread() {
			public void run() {
				data = getdb.getDataFromDB(day, hour);

				runOnUiThread(new Runnable() {
					public void run() {

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
						adc0 = parseJSON.getAdc0();
						digi0 = parseJSON.getDigi0();

						whichGraph = getIntent().getExtras().getString(
								"whichGraph");

						TimeGraph graph = new TimeGraph();

						if (whichGraph.equals("Humidity Chart")) {
							gView = graph.getView(getApplicationContext(),
									date, humidity, "Humidity");
						} else if (whichGraph.equals("Temperature Chart")) {
							gView = graph.getView(getApplicationContext(),
									date, temperature, "Temperature");
						} else if (whichGraph
								.equals("ADC0 Chart (Temperature)")) {
							gView = graph.getView(getApplicationContext(),
									date, adc0, "ADC0 (Temperature)");
						} else if (whichGraph.equals("DIGI0 Chart (Motion)")) {
							gView = graph.getView(getApplicationContext(),
									date, digi0, "DIGI0 (Motion)");
						}

						LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
						layout.addView(gView);

					}
				});
			}
		};
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
		case R.id.refresh:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc0.clear();
			digi0.clear();
			finish();
			Intent intent1 = new Intent(getApplicationContext(),
					GraphActivity.class);
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
			Intent intent2 = new Intent(this, GraphActivity.class);
			intent2.putExtra("dayOfMonth", 0);
			intent2.putExtra("hourOfDay", 24);
			intent2.putExtra("whichGraph",
					getIntent().getExtras().getString("whichGraph"));
			intent2.putExtra("refresh", "refresh24");
			startActivity(intent2);
			return true;
		case R.id.lastTwoDays:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc0.clear();
			digi0.clear();
			finish();
			Intent intent3 = new Intent(this, GraphActivity.class);
			intent3.putExtra("dayOfMonth", 2);
			intent3.putExtra("hourOfDay", 0);
			intent3.putExtra("whichGraph",
					getIntent().getExtras().getString("whichGraph"));

			startActivity(intent3);
			return true;
		case R.id.lastWeek:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc0.clear();
			digi0.clear();
			finish();
			Intent intent4 = new Intent(this, GraphActivity.class);
			intent4.putExtra("dayOfMonth", 7);
			intent4.putExtra("hourOfDay", 0);
			intent4.putExtra("whichGraph",
					getIntent().getExtras().getString("whichGraph"));

			startActivity(intent4);
			return true;
		case R.id.lastMonth:
			date.clear();
			humidity.clear();
			temperature.clear();
			adc0.clear();
			digi0.clear();
			finish();
			Intent intent5 = new Intent(this, GraphActivity.class);
			intent5.putExtra("dayOfMonth", 30);
			intent5.putExtra("hourOfDay", 0);
			intent5.putExtra("whichGraph",
					getIntent().getExtras().getString("whichGraph"));

			startActivity(intent5);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}