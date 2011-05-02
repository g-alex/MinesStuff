package fr.agarnier.jrobintest;

import fr.lelouet.consumption.basic.BasicConsumptionList;
import fr.lelouet.consumption.model.ConsumptionList;
import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import static org.jrobin.core.ConsolFuns.*;
import static org.jrobin.core.DsTypes.*;
import org.jrobin.core.RrdDb;
import org.jrobin.core.RrdDef;
import org.jrobin.core.RrdException;
import org.jrobin.core.Sample;
import org.jrobin.graph.RrdGraph;
import org.jrobin.graph.RrdGraphDef;
import org.xml.sax.SAXException;

/**
 * 
 * @author agarnier
 */
public class App {

    public static void main(String[] args) throws RrdException, IOException, ParserConfigurationException, SAXException {

        ConsumptionList list = App.getList();
        int step = (int) Math.max(1, (list.getMaxTime() - list.getMinTime()) / 400000);
        long startTime = (list.getMinTime() / 1000) - 1;

        RrdDef rrdDef = App.buildRrdDef(startTime, step, list);
        App.buildRrdDb(rrdDef, step, list);

        App.parseXml();

        App.pngGraph(rrdDef, list);

    } // static void main(String[])

    private static ConsumptionList getList() {
        ConsumptionList list = new BasicConsumptionList();
        for (long l = 1303217634820L; l < 1303217634820L + 3600 * 1000; l += 5789) {
            list.addData(l, ((double) (int) (((Math.random() * 1.3) + 27.4) * 10)) / 10);
        }
        return list;
    } // static ConsumptionList getList()

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
    } // static String parseDate(long)

    private static RrdDef buildRrdDef(long startTime, int step, ConsumptionList list) throws RrdException {
        RrdDef rrdDef = new RrdDef("src/main/resources/rrd/watt.rrd", startTime, step);
        rrdDef.addDatasource("Watt", DT_GAUGE, step, 0, Double.NaN);
        rrdDef.addArchive(CF_LAST, 0.5, 1, (int) (list.getMaxTime() - list.getMinTime()) / (1000 * step));
        return rrdDef;
    } // static RrdDef buildRrdDef(long , int , ConsumptionList)

    private static void buildRrdDb(RrdDef rrdDef, int step, ConsumptionList list) throws RrdException, IOException {
        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        for (long time = list.getMinTime(); time < list.getMaxTime(); time += 1000 * step) {
            sample.setTime(time / 1000);
            sample.setValue("Watt", list.getConsumption(time));
            sample.update();
        } // for
        rrdDb.dumpXml("src/main/resources/xml/watt.xml");
        rrdDb.close();
    } // static void buildRrdDb(RrdDef , int , ConsumptionList)

    private static void pngGraph(RrdDef rrdDef, ConsumptionList list) throws RrdException, IOException {
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
    } // static void pngGraph(RrdDef , ConsumptionList)

    private static void parseXml() throws ParserConfigurationException, SAXException, IOException {
        SAXParser xmlParser = SAXParserFactory.newInstance().newSAXParser();
        WattConsumption wattCons = new WattConsumption();
        xmlParser.parse("src/main/resources/xml/watt.xml", wattCons);
        for (Double watt : wattCons.getWatts()) {
            System.out.println(watt);
        } // for
    }
} // class App

