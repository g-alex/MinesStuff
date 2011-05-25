package btr.fr.garnier.hiberjreports;

import btr.fr.garnier.btrpersist.Persist;
import btr.fr.garnier.btrpersist.PersistSession;
import btr.fr.garnier.hiberjreports.hibernate.MachineConsumption;
import btr.fr.garnier.hiberjreports.hibernate.Metrics;
import java.util.ArrayList;
import java.util.List;
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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

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

    private static ProjectionList addMinMaxAvg(ProjectionList origin, String field) {
        String Capsed = "" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
        return origin.add(Projections.alias(Projections.min(field), "min" + Capsed)).
                add(Projections.alias(Projections.max(field), "max" + Capsed)).
                add(Projections.alias(Projections.avg(field), "avg" + Capsed));
    }

    private static List<Metrics> getPersistedMetrics() {
        PersistSession persistSession = Persist.openSession(MachineConsumption.class);
        List<Object[]> brutList = persistSession.createCriteria().setProjection(Projections.projectionList().
                add(Projections.groupProperty("category"), "type").
                add(Projections.property("category"), "machine").
                add(Projections.alias(Projections.min("watt"), "minWatt")).
                add(Projections.alias(Projections.max("watt"), "maxWatt")).
                add(Projections.alias(Projections.avg("watt"), "avgWatt")).
                add(Projections.alias(Projections.min("cpu"), "minCpu")).
                add(Projections.alias(Projections.max("cpu"), "maxCpu")).
                add(Projections.alias(Projections.avg("cpu"), "avgCpu")).
                add(Projections.alias(Projections.min("ram"), "minRam")).
                add(Projections.alias(Projections.max("ram"), "maxRam")).
                add(Projections.alias(Projections.avg("ram"), "avgRam"))).list();
        brutList.addAll(persistSession.createCriteria().setProjection(Projections.projectionList().
                add(Projections.alias(Projections.property("category"), "type")).
                add(Projections.alias(Projections.groupProperty("nom"), "machine")).
                add(Projections.alias(Projections.min("watt"), "minWatt")).
                add(Projections.alias(Projections.max("watt"), "maxWatt")).
                add(Projections.alias(Projections.avg("watt"), "avgWatt")).
                add(Projections.alias(Projections.min("cpu"), "minCpu")).
                add(Projections.alias(Projections.max("cpu"), "maxCpu")).
                add(Projections.alias(Projections.avg("cpu"), "avgCpu")).
                add(Projections.alias(Projections.min("ram"), "minRam")).
                add(Projections.alias(Projections.max("ram"), "maxRam")).
                add(Projections.alias(Projections.avg("ram"), "avgRam"))).list());
        List<Metrics> metricsList = App.parseMetrics(brutList);
        persistSession.close();
        return metricsList;
    } // List<Metrics> getMetrics()

    private static List<Metrics> parseMetrics(List<Object[]> list) {
        List<Metrics> metricsList = new ArrayList<Metrics>();
        for (Object[] objects : list) {
            Metrics metrics = new Metrics();
            metrics.setAttributes(objects);
            metricsList.add(metrics);
        } // for
        return metricsList;
    } // List<Metrics> parseMetrics(List)
}
