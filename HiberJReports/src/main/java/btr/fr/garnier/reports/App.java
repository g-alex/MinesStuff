package btr.fr.garnier.reports;

import btr.fr.garnier.persist.Persist;
import btr.fr.garnier.persist.SelectOperation;
import btr.fr.garnier.model.MachineConsumption;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * Reports generation application based on JasperReports & iReport.
 *
 * @author agarnier
 */
public class App {

    public static void main(String[] args) throws JRException, NoSuchFieldException {

        JasperDesign jspDesign = JRXmlLoader.load("src/main/resources/"
                + "jasperreports/hiberReport.jrxml");
        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jspReport, null,
                new JRBeanCollectionDataSource(App.getPersistedMetrics()));

        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/"
                + "resources/jasperreports/hiberReport.pdf");

        JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "src/"
                + "main/resources/jasperreports/hiberReport.xls");
        exporterXls.exportReport();

    } // static void maint(String[])

    private static List<Metric> getPersistedMetrics() throws NoSuchFieldException {
        Map<Field, SelectOperation> allFields = new LinkedHashMap<Field, SelectOperation>();
        allFields.put(MachineConsumption.class.getDeclaredField("category"), SelectOperation.IDGROUP);
        allFields.putAll(App.addSelField(SelectOperation.MMA, "watt", "cpu", "ram"));

        Map<Field, SelectOperation> eachFields = new LinkedHashMap<Field, SelectOperation>();
        eachFields.put(MachineConsumption.class.getDeclaredField("category"), SelectOperation.IDENT);
        eachFields.put(MachineConsumption.class.getDeclaredField("nom"), SelectOperation.GROUP);
        eachFields.putAll(App.addSelField(SelectOperation.MMA, "watt", "cpu", "ram"));

        List parsedMetrics = parseMetrics(Persist.select(MachineConsumption.class, allFields), GroupField.CATEG);
        parsedMetrics.addAll(parseMetrics(Persist.select(MachineConsumption.class, eachFields), GroupField.NAME));
        return parsedMetrics;
    } // List<Metric> getMetrics()

    private static Map<Field, SelectOperation> addSelField(SelectOperation selectOperation, String... fields)
            throws NoSuchFieldException {
        Map<Field, SelectOperation> selectFieldsMap = new LinkedHashMap<Field, SelectOperation>();
        for (String field : fields) {
            selectFieldsMap.put(MachineConsumption.class.getDeclaredField(field), selectOperation);
        } // for
        return selectFieldsMap;
    } // Map<Field, SelectOperation> addMinMaxAvg(Map<Field, SelectOperation>, String...)

    private static List<Metric> parseMetrics(List<Object[]> list, GroupField groupField) {
        List<Metric> metricsList = new ArrayList<Metric>();
        for (Object[] objects : list) {
            metricsList.add(buildMetric(objects, groupField));
        } // for
        return metricsList;
    } // List<Metric> parseMetrics(List)

    private static Metric buildMetric(Object[] objects, GroupField groupField)
            throws ArrayIndexOutOfBoundsException {
        Metric metric = new Metric();
        metric.setType(groupField.categoryToType(objects[0].toString()));
        metric.setMachine(groupField.nameToMachine(objects[1].toString()));
        metric.setMinWatt((Double) objects[2]);
        metric.setMaxWatt((Double) objects[3]);
        metric.setAvgWatt((Double) objects[4]);
        metric.setMinCpu((Double) objects[5]);
        metric.setMaxCpu((Double) objects[6]);
        metric.setAvgCpu((Double) objects[7]);
        metric.setMinRam((Double) objects[8]);
        metric.setMaxRam((Double) objects[9]);
        metric.setAvgRam((Double) objects[10]);
        return metric;
    }
}
