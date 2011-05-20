package btr.fr.garnier.hiberjreports;

import btr.fr.garnier.btrpersist.Persist;
import btr.fr.garnier.hiberjreports.hibernate.Metrics;
import java.lang.reflect.Field;
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
                new JRBeanCollectionDataSource(Persist.get(Metrics.class,
                Metrics.class.getDeclaredField("type"))));

        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/"
                + "resources/jasperreports/hiberReport.pdf");

        JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "src/"
                + "main/resources/jasperreports/hiberReport.xls");
        exporterXls.exportReport();

    } // static void maint(String[])
} // class App

