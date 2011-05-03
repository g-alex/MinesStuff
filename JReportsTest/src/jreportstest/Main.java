/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jreportstest;

import com.mysql.jdbc.Driver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author agarnier
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args)
            throws JRException, FileNotFoundException, IOException, SQLException {

        Driver monDriver = new Driver();
        DriverManager.registerDriver(monDriver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jasper_database", "root", "tototiti");

        JasperDesign jspDesign = JRXmlLoader.load("src/reports/JDBCreport.jrxml");
        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jspReport, null, connection);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/reports/JDBCreport.pdf");

        JRXlsExporter exporterXls = new JRXlsExporter();
        exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "src/reports/JDBCreport.xls");
        exporterXls.exportReport();

    }
}
