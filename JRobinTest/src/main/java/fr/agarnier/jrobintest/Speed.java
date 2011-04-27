/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.agarnier.jrobintest;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jrobin.core.RrdDb;
import org.jrobin.core.RrdDef;
import org.jrobin.core.RrdException;
import org.jrobin.core.Sample;
import org.jrobin.graph.RrdGraph;
import org.jrobin.graph.RrdGraphDef;
import static org.jrobin.core.ConsolFuns.*;
import static org.jrobin.core.DsTypes.*;

/**
 *
 * @author bobi
 */
public class Speed {

    private static Map<Long, Double> VITESSES;

    private static void setVITESSES() {
        Speed.VITESSES = new LinkedHashMap<Long, Double>();
        Speed.VITESSES.put(new Long(1051481100), new Double(12345));
        Speed.VITESSES.put(new Long(1051481400), new Double(12357));
        Speed.VITESSES.put(new Long(1051481700), new Double(12363));
        Speed.VITESSES.put(new Long(1051482000), new Double(12363));
        Speed.VITESSES.put(new Long(1051482300), new Double(12363));
        Speed.VITESSES.put(new Long(1051482600), new Double(12373));
        Speed.VITESSES.put(new Long(1051482900), new Double(12383));
        Speed.VITESSES.put(new Long(1051483200), new Double(12393));
        Speed.VITESSES.put(new Long(1051483500), new Double(12399));
        Speed.VITESSES.put(new Long(1051483800), new Double(12405));
        Speed.VITESSES.put(new Long(1051484100), new Double(12411));
        Speed.VITESSES.put(new Long(1051484400), new Double(12415));
        Speed.VITESSES.put(new Long(1051484700), new Double(12420));
        Speed.VITESSES.put(new Long(1051485000), new Double(12422));
        Speed.VITESSES.put(new Long(1051485300), new Double(12423));
    }

    private static void graphTest() throws RrdException, IOException {
        long startTime = 1051480800;
        RrdDef rrdDef = new RrdDef("src/main/resources", startTime, 600);
        rrdDef.addDatasource("vitesse", DT_COUNTER, 600, 0, Double.NaN);
        rrdDef.addArchive(CF_AVERAGE, 0.5, 1, 24);

        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        for (Map.Entry<Long, Double> e : Speed.VITESSES.entrySet()) {
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
        gDef.hrule(100, Color.BLUE, "limite");
        gDef.area("bonne", new Color(0, 128, 0), "bonne");
        gDef.area("rapide", new Color(128, 0, 0), "rapide");
        gDef.stack("over", Color.RED, "over");
        gDef.setImageFormat("png");

        // then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified
    }
}
