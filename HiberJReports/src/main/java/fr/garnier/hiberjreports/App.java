package fr.garnier.hiberjreports;

import fr.garnier.hiberjreports.hibernate.HibernateUtil;
import fr.garnier.hiberjreports.hibernate.MachineConsumption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws JRException {

        Session session = HibernateUtil.getSessionFactory().openSession();

//        for (Object entry : session.createQuery("from MachineConsumption").list()) {
//            System.out.println((MachineConsumption) entry);
//        } // for

        JasperDesign jspDesign = JRXmlLoader.load("src/main/resources/jasperreports/hiberReport.jrxml");
        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);



        JasperPrint jasperPrint = JasperFillManager.fillReport(jspReport, null, new JRBeanCollectionDataSource(session.createQuery("from MachineConsumption").list()));

        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/jasperreports/hiberReport.pdf");

    } // static void maint(String[])
} // class App

