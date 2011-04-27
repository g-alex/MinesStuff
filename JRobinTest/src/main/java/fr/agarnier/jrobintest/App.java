package fr.agarnier.jrobintest;

import fr.lelouet.consumption.basic.BasicConsumptionList;
import fr.lelouet.consumption.model.ConsumptionList;
import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;
import static org.jrobin.core.ConsolFuns.*;
import static org.jrobin.core.DsTypes.*;
import org.jrobin.core.RrdDb;
import org.jrobin.core.RrdDef;
import org.jrobin.core.RrdException;
import org.jrobin.core.Sample;
import org.jrobin.graph.RrdGraph;
import org.jrobin.graph.RrdGraphDef;

/**
 * 
 * @author agarnier
 */
public class App {

    private static int STEP = 15;

    public static void main(String[] args) throws RrdException, IOException {

        ConsumptionList list = App.getList();

        long startTime = (list.getMinTime() / 1000) - 1;

        RrdDef rrdDef = new RrdDef("src/main/resources/rrd/watt.rrd", startTime, App.STEP);
        rrdDef.addDatasource("Watt", DT_GAUGE, App.STEP, 0, Double.NaN);
        rrdDef.addArchive(CF_LAST, 0.5, 1, (int) (list.getMaxTime() - list.getMinTime()) / (1000 * App.STEP));

        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        for (long time = list.getMinTime(); time < list.getMaxTime(); time += 1000 * App.STEP) {
            sample.setTime(time / 1000);
            sample.setValue("Watt", list.getConsumption(time));
            sample.update();
        }
        rrdDb.close();

        RrdGraphDef gDef = new RrdGraphDef();
        gDef.setWidth(500);
        gDef.setHeight(300);
        gDef.setFilename("src/main/resources/graphs/graphWatt.png");
        gDef.setStartTime(list.getMinTime() / 1000);
        gDef.setEndTime(list.getMaxTime() / 1000);

        gDef.setColor("CANVAS", new Color(12, 24, 24));
        gDef.setTitle("Consumption graph | " + App.parseDate(list.getMinTime())
                + " - " + App.parseDate(list.getMaxTime()));
        gDef.setVerticalLabel("Watt");

        gDef.datasource("Watt", rrdDef.getPath(), "Watt", CF_LAST);
        gDef.line("Watt", Color.GREEN, "Watt consumption");

        gDef.setImageFormat("png");

        // then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified
    } // static void main(String[])

    static ConsumptionList getList() {
        ConsumptionList list = new BasicConsumptionList();
        for (long l = 1303217634820L; l < 1303217634820L + 1000 * 5789; l += 5789) {
            list.addData(l, ((double) (int) (((Math.random() * 1.3) + 27.4) * 10)) / 10);
        }
        return list;
    }

    private static String parseDate(long Time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Time);
        int month = cal.get(Calendar.MONTH) + 1;
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        return cal.get(Calendar.DATE) + "/" + (month < 10 ? 0 : "") + month
                + "/" + cal.get(Calendar.YEAR) + "~"
                + cal.get(Calendar.HOUR_OF_DAY) + ":" + (minute < 10 ? 0 : "")
                + minute + ":" + (second < 10 ? 0 : "") + second;
    }
} // class App

