package fr.chavrier.entropyWebManager.charts;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

public class LineChartEngine extends JFreeChartEngine {

    public static Integer strokeWidth = 2;
    public static boolean showLine = true;
    public static boolean lineShape = true;

    public LineChartEngine() {
    }

    public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        jfchart.setBorderVisible(false);
        jfchart.setBackgroundPaint(Color.white);
        chart.setBgColor("#FFFFFF");
        return false;
    }
}
