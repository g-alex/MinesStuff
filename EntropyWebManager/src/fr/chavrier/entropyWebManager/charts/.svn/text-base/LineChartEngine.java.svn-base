package fr.chavrier.entropyWebManager.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;
 
public class LineChartEngine extends JFreeChartEngine {
    public LineChartEngine() {
    }
 
    public static Integer strokeWidth = 2;
    public static boolean showLine = true;
    public static boolean lineShape = true;
 
    public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
        jfchart.setBorderVisible(false);
        jfchart.setBackgroundPaint(Color.white);
        chart.setBgColor("#FFFFFF");
        return false;
    }
}
