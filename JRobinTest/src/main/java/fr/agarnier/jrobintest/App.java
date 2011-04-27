package fr.agarnier.jrobintest;

import fr.lelouet.consumption.basic.BasicConsumptionList;
import fr.lelouet.consumption.model.ConsumptionList;
import java.awt.Color;
import java.io.IOException;
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

    public static void main(String[] args) throws RrdException, IOException {
        ConsumptionList list = App.getList();

        long startTime = (list.getMinTime() / 1000) - 1;

        RrdDef rrdDef = new RrdDef("src/main/resources/rrd/watt.rrd", startTime, 5);
        rrdDef.addDatasource("Watt", DT_GAUGE, 5, 0, Double.NaN);
        rrdDef.addArchive(CF_LAST, 0.1, 1, 20);

        RrdDb rrdDb = new RrdDb(rrdDef);
        Sample sample = rrdDb.createSample();
        for (long time = list.getMinTime(); time < list.getMaxTime(); time += 5000) {
            sample.setTime(time / 1000);
            sample.setValue("Watt", list.getConsumption(time));
            sample.update();
        }
        rrdDb.close();
        RrdGraphDef gDef = new RrdGraphDef();
        gDef.setWidth(500);
        gDef.setHeight(300);
        gDef.setFilename("src/main/resources/graphs/graphWatt.png");
        gDef.setStartTime((list.getMinTime()/1000)+6);
        gDef.setEndTime((list.getMaxTime()/1000)-5);
        gDef.setTitle("Watt");
        gDef.setVerticalLabel("Watt");

        gDef.datasource("Watt", rrdDef.getPath(), "Watt", CF_LAST);
        gDef.line("Watt", Color.BLUE, "Watt");
        gDef.setImageFormat("png");

        // then actually draw the graph
        RrdGraph graph = new RrdGraph(gDef); // will create the graph in the path specified
    } // static void main(String[])

    static ConsumptionList getList() {
        ConsumptionList list = new BasicConsumptionList();
        list.addData(1303217634820L, 36.1D);
        list.addData(1303217640609L, 28.0D);
        list.addData(1303217646394L, 27.5D);
        list.addData(1303217652180L, 27.8D);
        list.addData(1303217657974L, 27.6D);
        list.addData(1303217663760L, 27.8D);
        list.addData(1303217669545L, 27.4D);
        list.addData(1303217675332L, 27.9D);
        list.addData(1303217681119L, 27.6D);
        list.addData(1303217686906L, 28.7D);
        list.addData(1303217692692L, 28.6D);
        list.addData(1303217698478L, 28.7D);
        list.addData(1303217704373L, 27.9D);
        list.addData(1303217710159L, 27.6D);
        list.addData(1303217715946L, 28.4D);
        list.addData(1303217721738L, 28.4D);
        list.addData(1303217727528L, 28.6D);
        list.addData(1303217733317L, 28.7D);
        list.addData(1303217739101L, 28.3D);
        list.addData(1303217744890L, 28.5D);
        return list;
    }
} // class App

