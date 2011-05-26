package btr.fr.garnier.hiberjreports;

import btr.fr.garnier.hiberjreports.hibernate.SelectFilter;
import btr.fr.garnier.btrpersist.Persist;
import btr.fr.garnier.btrpersist.SelectOperation;
import btr.fr.garnier.hiberjreports.hibernate.MachineConsumption;
import btr.fr.garnier.hiberjreports.hibernate.Metrics;
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

    private static List<Metrics> getPersistedMetrics() throws NoSuchFieldException {
        Map<Field, SelectOperation> allFields = new LinkedHashMap<Field, SelectOperation>();
        allFields.put(MachineConsumption.class.getDeclaredField("category"), SelectOperation.IDGROUP);
        allFields.putAll(App.addSelOp(SelectOperation.MMA, "watt", "cpu", "ram"));

        Map<Field, SelectOperation> eachFields = new LinkedHashMap<Field, SelectOperation>();
        eachFields.put(MachineConsumption.class.getDeclaredField("category"), SelectOperation.IDENT);
        eachFields.put(MachineConsumption.class.getDeclaredField("nom"), SelectOperation.GROUP);
        eachFields.putAll(App.addSelOp(SelectOperation.MMA, "watt", "cpu", "ram"));

        List parsedMetrics = parseMetrics(Persist.select(MachineConsumption.class, allFields), SelectFilter.ALL);
        parsedMetrics.addAll(parseMetrics(Persist.select(MachineConsumption.class, eachFields), SelectFilter.EACH));
        return parsedMetrics;
    } // List<Metrics> getMetrics()

    private static Map<Field, SelectOperation> addSelOp(SelectOperation selectOperation, String... fields)
            throws NoSuchFieldException {
        Map<Field, SelectOperation> selectFieldsMap = new LinkedHashMap<Field, SelectOperation>();
        for (String field : fields) {
            selectFieldsMap.put(MachineConsumption.class.getDeclaredField(field), selectOperation);
        } // for
        return selectFieldsMap;
    } // Map<Field, SelectOperation> addMinMaxAvg(Map<Field, SelectOperation>, String...)

    private static List<Metrics> parseMetrics(List<Object[]> list, SelectFilter select) {
        List<Metrics> metricsList = new ArrayList<Metrics>();
        for (Object[] objects : list) {
            Metrics metrics = new Metrics();
            metrics.setAttributes(objects, select);
            metricsList.add(metrics);
        } // for
        return metricsList;
    } // List<Metrics> parseMetrics(List)
}
