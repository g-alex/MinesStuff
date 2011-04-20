/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jreportstest;

import groovy.lang.Tuple;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * @author agarnier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static Map<String, Class> FIELDS = new HashMap<String, Class>();

    public static void main(String[] args)
            throws JRException, FileNotFoundException, IOException {

        parseData();

//        JasperDesign jspDesign = JRXmlLoader.load("src/reports/reportTest.jrxml");
//        JasperReport jspReport = JasperCompileManager.compileReport(jspDesign);
//
//        JRCsvDataSource csvSource = new JRCsvDataSource("src/reports/reportTest.csv");
//        csvSource.setUseFirstRowAsHeader(true);
//        csvSource.setFieldDelimiter(',');
//        csvSource.setRecordDelimiter("\n");
//
//        Map params = new HashMap();
//        params.put("Titre", "Titre");
//        byte[] bytes = JasperRunManager.runReportToPdf(jspReport, params, csvSource);
//
//        FileOutputStream fos = new FileOutputStream("src/reports/reportTest.pdf");
//        fos.write(bytes, 0, bytes.length);
//        fos.flush();
//        fos.close();
    }

    private static void parseData() throws FileNotFoundException, IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream("src/reports/reportTest.csv"));
        LineNumberReader lnr = new LineNumberReader(isr);
        String line;
        List<Long> times = new ArrayList<Long>();
        List<Double> consumptions = new ArrayList<Double>();
        while ((line = lnr.readLine()) != null) {
            if (lnr.getLineNumber() > 1) {
                String[] fields = line.split(",");
                times.add(Long.decode(fields[0]));
                consumptions.add(Double.valueOf(fields[1]).doubleValue());
            } // if
        } // while
        String überString = "min,max,moy\n" + min(consumptions) + ","
                + max(consumptions) + "," + moy(consumptions);
        System.out.println(überString);
        System.out.println(max(consumptions));
        System.out.println(min(consumptions));
        System.out.println(moy(consumptions));
    } // void parseData()

    private static Double max(List<Double> values) {
        Double max = null;
        for (Double val : values) {
            max = (max == null || val > max) ? val : max;
        } // for
        return max;
    }

    private static Double min(List<Double> values) {
        Double min = null;
        for (Double val : values) {
            min = (min == null || val < min) ? val : min;
        } // for
        return min;
    }

    private static Double moy(List<Double> values) {
        double moy = 0;
        for (double val : values) {
            moy += val;
        } // for
        return new Double((double) (int) (10 * moy / values.size()) / 10);
    }
}
