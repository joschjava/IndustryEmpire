package gui;

import java.util.ArrayList;

import game.ResourceSpec;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import objects.HistoryObject;

public class ResourceChart {

	double[] rawSeries;
	final LineChart<Number,Number> lineChart; 
	
	
	public ResourceChart(){
        //defining the axes
   	 final NumberAxis xAxis = new NumberAxis();
   	 final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setCreateSymbols(false);
	}
	
	public void addSeries(ArrayList<HistoryObject> history, ResourceSpec resSpec) {
        //defining a series
        Series<Number, Number> series = new XYChart.Series<Number, Number>();
        for (int i = 0; i < history.size(); i++) {
        	HistoryObject curHistory = history.get(i);
        	series.getData().add(new XYChart.Data<Number, Number>(curHistory.getTime(), curHistory.getValue()));
		}
        series.setName(resSpec.getName());
        lineChart.getData().add(series);
        lineChart.setCursor(Cursor.CROSSHAIR);
	}
	
	public Scene getStage() {
        Scene scene  = new Scene(lineChart,800,600);
        return scene;
	}
    
}
