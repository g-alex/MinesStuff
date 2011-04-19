/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jreportstest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author bobi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
            throws JRException, FileNotFoundException, IOException {
        JasperDesign jspDesign = JRXmlLoader.load("src/reports/reportTest.jrxml");
        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);
        JRCsvDataSource csvSource = new JRCsvDataSource("src/reports/reportTest.csv");
        csvSource.setUseFirstRowAsHeader(true);
        csvSource.setFieldDelimiter(',');
        csvSource.setRecordDelimiter("\n");
        Map params = new HashMap();
        params.put("Titre", "Titre");
        byte[] bytes = JasperRunManager.runReportToPdf(jspReport, params, csvSource);
        FileOutputStream fos = new FileOutputStream("src/reports/reportTest.pdf");
        fos.write(bytes, 0, bytes.length);
        fos.flush();
        fos.close();
    }
}
