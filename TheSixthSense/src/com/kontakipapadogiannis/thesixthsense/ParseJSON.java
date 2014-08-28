package com.kontakipapadogiannis.thesixthsense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

public class ParseJSON {

	static ArrayList<Date> dates = new ArrayList<Date>();
	static ArrayList<Double> humidity = new ArrayList<Double>();
	static ArrayList<Double> temperature = new ArrayList<Double>();
	static ArrayList<Double> adc = new ArrayList<Double>();
	static ArrayList<Double> digi = new ArrayList<Double>();

	@SuppressLint("SimpleDateFormat")
	public static void parse(String result) throws ParseException {

		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);

				String result_time = json_data.getString("result_time");
				java.util.Date dateParser = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").parse(result_time);
				dates.add((Date) dateParser);

				if(json_data.getString("humid") != "null"){
					Double humid = (Double.parseDouble(json_data.getString("humid")));
					humidity.add((double) (Math.round(((0.0405*(humid)-4-humid*humid*0.0000028))*100))/100);
				}else{
					humidity.add(0.0);
				}
				
				
				if(json_data.getString("humtemp") != "null"){
				Double humtemp = Double.parseDouble(json_data
						.getString("humtemp"));
				temperature.add((double) ((Math.round((humtemp*0.98-3840)/100)*100)/100));
				}else{
					temperature.add(0.0);
				}

				if(json_data.getString("adc0") != "null"){
				Double adc0 = Double.parseDouble(json_data.getString("adc0"));
				adc.add((double) ((Math.round((adc0*0.98-3840)/100)*100)/100));
				}else{
					adc.add(0.0);
				}

				if(json_data.getString("digi0") != "null"){
				Double digi0 = Double.parseDouble(json_data.getString("digi0"));
				digi.add(digi0);
				}else{
					digi.add(0.0);
				}

			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
	}

	public ArrayList<Date> getDate() {
		return dates;
	}

	public ArrayList<Double> getHumid() {
		return humidity;
	}

	public ArrayList<Double> getHumtemp() {
		return temperature;
	}

	public ArrayList<Double> getAdc0() {
		return adc;
	}

	public ArrayList<Double> getDigi0() {
		return digi;
	}

}
