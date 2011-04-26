package fr.agarnier.jrobintest;

import fr.lelouet.consumption.basic.BasicConsumptionList;
import fr.lelouet.consumption.model.ConsumptionList;
import java.awt.Color;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jrobin.core.RrdDb;
import static org.jrobin.core.ConsolFuns.*;
import static org.jrobin.core.DsTypes.*;
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

    private static String PACKAGE = "org.jrobin.core";
    private static Map<Long, Double> VITESSES;
    private static Map<Long, Double> WATT;

    public static void main(String[] args) throws Exception {
        App.setVITESSES();
        long startTime = App.setWATT();
        App.graphTest();
        App.graphWatt(startTime - 5);
    } // static void main(String[])

    private static void setVITESSES() {
        App.VITESSES = new LinkedHashMap<Long, Double>();
        App.VITESSES.put(new Long(1051481100), new Double(12345));
        App.VITESSES.put(new Long(1051481400), new Double(12357));
        App.VITESSES.put(new Long(1051481700), new Double(12363));
        App.VITESSES.put(new Long(1051482000), new Double(12363));
        App.VITESSES.put(new Long(1051482300), new Double(12363));
        App.VITESSES.put(new Long(1051482600), new Double(12373));
        App.VITESSES.put(new Long(1051482900), new Double(12383));
        App.VITESSES.put(new Long(1051483200), new Double(12393));
        App.VITESSES.put(new Long(1051483500), new Double(12399));
        App.VITESSES.put(new Long(1051483800), new Double(12405));
        App.VITESSES.put(new Long(1051484100), new Double(12411));
        App.VITESSES.put(new Long(1051484400), new Double(12415));
        App.VITESSES.put(new Long(1051484700), new Double(12420));
        App.VITESSES.put(new Long(1051485000), new Double(12422));
        App.VITESSES.put(new Long(1051485300), new Double(12423));
    }

    private static long setWATT() {
        App.WATT = new LinkedHashMap<Long, Double>();
        App.WATT.put(1303217652180L, 27.8D);
        App.WATT.put(1303217657974L, 27.6D);
        App.WATT.put(1303217663760L, 27.8D);
        App.WATT.put(1303217669545L, 27.4D);
        App.WATT.put(1303217675332L, 27.9D);
        App.WATT.put(1303217681119L, 27.6D);
        App.WATT.put(1303217686906L, 28.7D);
        App.WATT.put(1303217692692L, 28.6D);
        App.WATT.put(1303217698478L, 28.7D);
        App.WATT.put(1303217704373L, 27.9D);
        App.WATT.put(1303217710159L, 27.6D);
        App.WATT.put(1303217715946L, 28.4D);
        App.WATT.put(1303217721738L, 28.4D);
        App.WATT.put(1303217727528L, 28.6D);
        App.WATT.put(1303217733317L, 28.7D);
        App.WATT.put(1303217739101L, 28.3D);
        App.WATT.put(1303217744890L, 28.5D);
        return App.lol();
    }

    private static void graphTest() throws RrdException, IOException {
        long startTime = 1051480800;
        RrdDef rrdDef = new RrdDef(PACKAGE, startTime, 600);
        rrdDef.addDatasource("vitesse", DT_COUNTER, 600, 0, Double.NaN);
        rrdDef.addArchive(CF_AVERAGE, 0.5, 1, 24);

        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        for (Map.Entry<Long, Double> e : App.VITESSES.entrySet()) {
            sample.setTime(e.getKey());
            sample.setValue("vitesse", e.getValue());
            sample.update();
        }
        rrdDb.close();
        RrdGraphDef gDef = new RrdGraphDef();
        gDef.setWidth(500);
        gDef.setHeight(300);
        gDef.setFilename("src/main/resources/graphs/graphTest.png");
        gDef.setStartTime(startTime);
        gDef.setEndTime(startTime + 4200);
        gDef.setTitle("Teh graph lulz");
        gDef.setVerticalLabel("kmh");

        gDef.datasource("vitesse", rrdDef.getPath(), "vitesse", CF_AVERAGE);
        gDef.datasource("kmh", "vitesse,3600,*");
        gDef.datasource("bonne", "kmh,100,GT,0,kmh,IF");
        gDef.datasource("rapide", "kmh,100,GT,kmh,0,IF");
        gDef.datasource("over", "kmh,100,GT,100,kmh,-,0,IF");
        gDef.datasource("under", "kmh,100,GT,0,kmh,100,-,IF");
        gDef.hrule(100, Color.BLUE, "limite");
        gDef.area("bonne", new Color(0, 128, 0), "bonne");
        gDef.area("rapide", new Color(128, 0, 0), "rapide");
        gDef.stack("over", Color.RED, "over");
        gDef.stack("under", new Color(128, 128, 255), "under");
        gDef.setImageFormat("png");

        // then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified
    }

    private static void graphWatt(long startTime) throws RrdException, IOException {

        RrdDef rrdDef = new RrdDef(PACKAGE, startTime, 5);
        rrdDef.addDatasource("Watt", DT_GAUGE, 5, 0, Double.NaN);
        rrdDef.addArchive(CF_LAST, 0.5, 1, 20);
        rrdDef.addArchive(CF_AVERAGE, 0.5, 5, 20);

        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        int i = 0;
        for (Map.Entry<Long, Double> e : App.WATT.entrySet()) {
            sample.setTime(e.getKey());
            sample.setValue("Watt", e.getValue());
            sample.update();
        }
        rrdDb.close();
        RrdGraphDef gDef = new RrdGraphDef();
        gDef.setWidth(500);
        gDef.setHeight(300);
        gDef.setFilename("src/main/resources/graphs/graphWatt.png");
        gDef.setStartTime(startTime);
        gDef.setEndTime(startTime + 93);
        gDef.setTitle("Watt");
        gDef.setVerticalLabel("Watt");

        gDef.datasource("Watt", rrdDef.getPath(), "Watt", CF_LAST);
        gDef.line("Watt", Color.BLUE, "Watt");
        gDef.setImageFormat("png");

        // then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified
    }

    static long lol() {
        long result = Long.MAX_VALUE;
        ConsumptionList list = new BasicConsumptionList();
        for (Map.Entry<Long, Double> e : App.WATT.entrySet()) {
            list.addData(e.getKey(), e.getValue());
        }
        App.WATT.clear();
        for (long time = list.getMinTime(); time < list.getMaxTime(); time += 5000) {
            long meinTime = time / 1000;
            result = meinTime < result ? meinTime : result;
            App.WATT.put(meinTime, list.getConsumption(time));
        }
        return result;
    }
} // class App

