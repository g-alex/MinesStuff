package fr.agarnier.jrobintest;

import java.awt.Color;
import java.lang.reflect.Constructor;
import org.jrobin.core.RrdDb;
import static org.jrobin.core.ConsolFuns.*;
import static org.jrobin.core.DsTypes.*;
import org.jrobin.core.RrdDef;
import org.jrobin.core.Sample;
import org.jrobin.graph.RrdGraph;
import org.jrobin.graph.RrdGraphDef;

/**
 * 
 * @author agarnier
 */
public class App {

    private static String PACKAGE = "org.jrobin.core";

    public static void main(String[] args) throws Exception {
        for (Class jrClass : PackageReflects.getClassesForPackage(App.PACKAGE)) {
            for (Constructor clConstr : jrClass.getConstructors()) {
                System.out.println(clConstr.getName() + "("
                        + PackageReflects.parseParams(clConstr.getParameterTypes()) + ")");
            } // for
        } // for

        // first, define the RRD
        long startTime = System.currentTimeMillis();
        RrdDef rrdDef = new RrdDef(PACKAGE, startTime, 300);
        rrdDef.addDatasource("sun", DT_GAUGE, 600, 0, Double.NaN);
        rrdDef.addDatasource("shade", DT_GAUGE, 600, 0, Double.NaN);
        rrdDef.addArchive(CF_AVERAGE, 0.5, 1, 600); // 1 step, 600 rows
        rrdDef.addArchive(CF_AVERAGE, 0.5, 6, 700); // 6 steps, 700 rows
        rrdDef.addArchive(CF_MAX, 0.5, 1, 600);

        // then, create a RrdDb from the definition and start adding data
        RrdDb rrdDb = new RrdDb(rrdDef);
        System.err.println("test");
        Sample sample = rrdDb.createSample();
//while (...) {
//    sample.setTime(t);
//    sample.setValue("inbytes", ...);
//    sample.setValue("outbytes", ...);
//    sample.update();
//}
        rrdDb.close();

// then create a graph definition
        RrdGraphDef gDef = new RrdGraphDef();
        gDef.setWidth(500);
        gDef.setHeight(300);
        gDef.setFilename("/src/main/resources/graphTest.png");
        gDef.setStartTime(startTime);
        gDef.setEndTime(startTime + 600);
        gDef.setTitle("My Title");
        gDef.setVerticalLabel("bytes");

        gDef.datasource("bytes", rrdDef.getPath(), "sun", CF_AVERAGE);
        gDef.hrule(2568, Color.GREEN, "hrule");
        gDef.setImageFormat("png");

// then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified

    } // static void main(String[])
} // class App

