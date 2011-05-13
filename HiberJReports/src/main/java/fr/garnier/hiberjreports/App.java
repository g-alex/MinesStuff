package fr.garnier.hiberjreports;

import fr.garnier.hiberjreports.hibernate.HibernateUtil;
import fr.garnier.hiberjreports.hibernate.MachineConsumption;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws JRException {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(MachineConsumption.class);
        criteria.addOrder(Order.asc("type"));

        JasperDesign jspDesign = JRXmlLoader.load("src/main/resources/"
                + "jasperreports/hiberReport.jrxml");
        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jspReport, null,
                new JRBeanCollectionDataSource(criteria.list()));
        
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/"
                + "resources/jasperreports/hiberReport.pdf");

        JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "src/"
                + "main/resources/jasperreports/hiberReport.xls");
        exporterXls.exportReport();

    } // static void maint(String[])
} // class App

