package com.kontakipapadogiannis.thesixthsense;

import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class TimeGraph {

	public GraphicalView getView(Context context, ArrayList<Date> dates,
			ArrayList<Double> data, String title) {

		TimeSeries series = new TimeSeries("");

		for (int i = 0; i < dates.size(); i++) {

			series.add(dates.get(i), data.get(i));

		}

		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		mDataset.addSeries(series);
		renderer.setColor(Color.CYAN);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setShowLegendItem(false);
		mRenderer.setZoomEnabled(true);
		mRenderer.setPanEnabled(true);
		mRenderer.setChartTitle(title);
		mRenderer.setBarWidth(5);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setMarginsColor(Color.BLACK);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsPadding(10);
		mRenderer.setLabelsTextSize(12);
		mRenderer.setYAxisMin(0);
		mRenderer.setYAxisMax(100);
		mRenderer.setShowGrid(true);
		mRenderer.setGridColor(Color.WHITE);

		mRenderer.addSeriesRenderer(renderer);

		GraphicalView gView = ChartFactory.getTimeChartView(context, mDataset,
				mRenderer, "yyyy-MM-dd\nHH:mm:ss");

		return gView;
	}

}
