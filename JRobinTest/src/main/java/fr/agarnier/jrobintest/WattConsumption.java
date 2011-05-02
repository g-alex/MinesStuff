/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.agarnier.jrobintest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author agarnier
 */
public class WattConsumption extends DefaultHandler {

    private Double watt;
    private List<Double> watts;

    public List<Double> getWatts() {
        return this.watts;
    } // List<Double> getWatts()

    public WattConsumption() {
        super();
        this.watts = new ArrayList<Double>();
    } // WattConsumption()

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String val = new String(ch, start, length).trim();
        if (Pattern.compile("[\\+\\-]\\d\\.\\d{10}E\\-?\\d{2}").matcher(val).
                matches()) {
            this.watt = new Double(val.substring(1, val.length()));
            if (val.charAt(0) == '-') {
                this.watt = -this.watt;
            } // if
        } // if
    } // void characters(char[], int , int)

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("v")) {
            this.watts.add(this.watt);
        } // if
    } // void endElement(String , String , String)
} // class WattConsumption

