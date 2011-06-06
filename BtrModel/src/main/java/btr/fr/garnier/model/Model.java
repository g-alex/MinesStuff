/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import btr.fr.garnier.persist.Persist;
import btr.fr.garnier.persist.Selection;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author agarnier
 */
public class Model {

    /**
     * Persist consumptions for given machine type, name and time.
     *
     * @param category Machine type.
     * @param time Consumptions time.
     * @param name Machine name.
     * @param watt Watt consumption.
     * @param cpu CPU consumption.
     * @param ram RAM consumption.
     */
    public static void persistConsumptions(MachineType category, long time, String name, double watt, double cpu, double ram) {
        Persist.save(new MachineConsumption(category, time, name, watt, cpu, ram));
    } // static void persistConsumptions(MachineType, long, String, double, double, double)

    /**
     * Select metrics on MachineConsumption through BtrPersist.
     *
     * @return A list of metrics based on persisted machine consumptions in
     * BtrPersist.
     * @throws NoSuchFieldException
     */
    public static List<Metric> getPersistedMetrics() throws NoSuchFieldException {
        Map<Field, Selection> allFields = new LinkedHashMap<Field, Selection>();
        allFields.put(MachineConsumption.class.getDeclaredField("type"), Selection.IDGROUP);
        allFields.putAll(Model.addSelField(Selection.MMA, "watt", "cpu", "ram"));

        Map<Field, Selection> eachFields = new LinkedHashMap<Field, Selection>();
        eachFields.put(MachineConsumption.class.getDeclaredField("type"), Selection.IDENT);
        eachFields.put(MachineConsumption.class.getDeclaredField("name"), Selection.GROUP);
        eachFields.putAll(Model.addSelField(Selection.MMA, "watt", "cpu", "ram"));

        List parsedMetrics = Model.parseMetrics(Persist.select(MachineConsumption.class, allFields), GroupField.CATEG);
        parsedMetrics.addAll(Model.parseMetrics(Persist.select(MachineConsumption.class, eachFields), GroupField.NAME));
        return parsedMetrics;
    } // List<Metric> getMetrics()

    /**
     * Bind a selection with given fields in a map.
     *
     * @param selection Selection to apply.
     * @param fields Fields to select.
     * @return Map binding selection with each field.
     * @throws NoSuchFieldException
     */
    private static Map<Field, Selection> addSelField(Selection selection, String... fields)
            throws NoSuchFieldException {
        Map<Field, Selection> selectFieldsMap = new LinkedHashMap<Field, Selection>();
        for (String field : fields) {
            selectFieldsMap.put(MachineConsumption.class.getDeclaredField(field), selection);
        } // for
        return selectFieldsMap;
    } // Map<Field, Selection> addMinMaxAvg(Map<Field, Selection>, String...)

    /**
     * Set a metrics list based on list of arrays of objects generated by the
     * Hibernate Projections API, group by the given field.
     *
     * @param list List generated by the Projections API.
     * @param groupField Grouping field.
     * @return List of parsed metrics.
     */
    private static List<Metric> parseMetrics(List<Object[]> list, GroupField groupField) {
        List<Metric> metricsList = new ArrayList<Metric>();
        for (Object[] objects : list) {
            metricsList.add(Model.buildMetric(objects, groupField));
        } // for
        return metricsList;
    } // List<Metric> parseMetrics(List)

    /**
     * Build a metric based on an array of object and a grouping field.
     *
     * @param objects Array of objects giving the metric's attributes.
     * @param groupField Grouping field.
     * @return Built metric.
     * @throws ArrayIndexOutOfBoundsException
     */
    private static Metric buildMetric(Object[] objects, GroupField groupField)
            throws ArrayIndexOutOfBoundsException {
        // Catching exceptions is for communists!
        return new Metric(
                groupField.typeToType(objects[0].toString()), // type
                groupField.nameToMachine(objects[1].toString()), // machine
                Model.trim((Double) objects[2], 2), // minWatt
                Model.trim((Double) objects[3], 2), // maxWatt
                Model.trim((Double) objects[4], 2), // avgWatt
                Model.trim((Double) objects[5], 1), // minCpu
                Model.trim((Double) objects[6], 1), // maxCpu
                Model.trim((Double) objects[7], 1), // avgCpu
                Model.trim((Double) objects[8], 1), // minRam
                Model.trim((Double) objects[9], 1), // maxRam
                Model.trim((Double) objects[10], 1)); // avgRam
    } // static Metric buildMetric(Object[], GroupField)

    private static double trim(double d, double prec) {
        double coeff = Math.pow(10D, prec);
        return Math.floor(d * coeff) / coeff;
    }
}
