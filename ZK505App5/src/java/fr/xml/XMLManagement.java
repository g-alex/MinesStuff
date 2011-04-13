/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author bobi
 */
public final class XMLManagement {

    /*
     * CATALINA_HOME environment variable is the Tomcat installation path.
     * If the variable is not set, see:
     * http://yonaldi.wordpress.com/2007/04/09/setting-java_home-and-catalina_home/
     */
    public static final String CATALINA_HOME = System.getenv("CATALINA_HOME");

    // Build an XML tree from file <filename>
    public static Document openXML(String filename)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        return docFactory.newDocumentBuilder().parse(filename);
    } // Document openXML(String)

    // Save the value of content in file <fileName>
    public static void writeXML(String filename, String content) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(content);
        fw.close();
    } // void writeXML(String,String)

    // Set the value of host parameter in XML tree doc to given ip.
    public static String setIp(Document doc, String ip)
            throws TransformerConfigurationException, TransformerException, IOException {

        NodeList docChildren = doc.getChildNodes();
        for (int i = 0; i < docChildren.getLength(); ++i) {

            Node context = docChildren.item(i);
            if (context.getNodeName().equals("Context")) {

                NodeList contextChildren = context.getChildNodes();
                for (int j = 0; j < contextChildren.getLength(); ++j) {

                    Node host = contextChildren.item(j);
                    if (host.getNodeName().equals("Parameter")) {

                        Node hostName = host.getAttributes().getNamedItem("name");
                        Node hostValue = host.getAttributes().getNamedItem("value");
                        if ((hostName != null) && (hostName.getNodeValue().equals("host")) && !hostValue.getNodeValue().equals(ip)) {

                            hostValue.setNodeValue(ip);

                            Transformer transformer = TransformerFactory.newInstance().newTransformer();
                            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                            StreamResult result = new StreamResult(new StringWriter());
                            DOMSource source = new DOMSource(doc);
                            transformer.transform(source, result);

                            return result.getWriter().toString();

                        }
                        break;
                    } // if
                } // for
                break;
            } // if
        } // for
        return null;
    } // String setIp(Document,String)

    // Get the value of host parameter from XML tree.
    public static String getIp(Document doc)
            throws TransformerConfigurationException, TransformerException,
            IOException {

        NodeList docChildren = doc.getChildNodes();
        for (int i = 0; i < docChildren.getLength(); ++i) {

            Node context = docChildren.item(i);
            if (context.getNodeName().equals("Context")) {

                NodeList contextChildren = context.getChildNodes();
                for (int j = 0; j < contextChildren.getLength(); ++j) {

                    Node host = contextChildren.item(j);
                    if (host.getNodeName().equals("Parameter")) {

                        Node hostName = host.getAttributes().getNamedItem("name");
                        if ((hostName != null) && (hostName.getNodeValue().equals("host"))) {

                            return host.getAttributes().getNamedItem("value").getNodeValue();

                        } // if
                        break;
                    } // if
                } // for
                break;
            } // if
        } // for
        return null;
    } // String getIp(Document,String)
}
