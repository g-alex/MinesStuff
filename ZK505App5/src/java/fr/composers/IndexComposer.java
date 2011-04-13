/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.composers;

import fr.xml.XMLManagement;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
//import javax.servlet.ServletRegistration;
import org.w3c.dom.Document;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author agarnier
 */
public class IndexComposer extends GenericForwardComposer {

    private Map<String, String> ips;
    Listbox vncIp;
    Button goVnc;
    Textbox log;

    public IndexComposer() {
        super();

        this.ips = new HashMap<String, String>();
        this.ips.put("Georg@172.28.2.197", "172.28.2.197");
        this.ips.put("Mike@192.168.122.220", "192.168.122.220");
        this.ips.put("VraiTet3@192.168.122.164", "192.168.122.164");
    } // IndexComposer()

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        for (Map.Entry<String, String> e : this.ips.entrySet()) {
            this.vncIp.appendItem(e.getKey(), e.getValue());
        } // for

        ServletContext guacaContext = ((ServletContext) this.application.getNativeContext()).getContext("/guacamole");
//        for (Map.Entry<String, ? extends ServletRegistration> e : guacaContext.getServletRegistrations().entrySet()) {
//            this.log.setValue(this.log.getValue() + e.getKey() + ":" + e.getValue().toString() + "\n");
//        }

        log(this.parentDir(guacaContext.getRealPath(guacaContext.getContextPath()))); // THIS is hellish interesting
//        log(guacaContext.getRealPath(guacaContext.getContextPath())); // THIS is hellish interesting

        log("getRequestDispatcher: " + guacaContext.getRequestDispatcher(guacaContext.getContextPath()).toString());

        log(guacaContext.getResourcePaths("/WEB-INF/").toString());

//        if (guacaContext.setInitParameter("host", "172.28.2.197")) {
//            this.log.setValue(guacaContext.getInitParameter("host"));
//        } else {
//            this.log.setValue("Ya didn't stop teh guacamole servlet...");
//        }
    } // void doAfterCompose(Component)

    public void onClick$goVnc(Event event) throws Exception {

        String ip = (String) this.vncIp.getSelectedItem().getValue();
        if (this.checkIp(ip)) {
            Document doc = XMLManagement.openXML(XMLManagement.CATALINA_HOME + "/conf/Catalina/localhost/guacamole.xml");
            String xmlContent = XMLManagement.setIp(doc, ip);
            if (xmlContent != null) {
                XMLManagement.writeXML(XMLManagement.CATALINA_HOME + "/conf/Catalina/localhost/guacamole.xml", xmlContent);
            } // if

            Executions.sendRedirect("/wait.zul");
        } // if
    } // void setXML(String)

    // check if given ip is a well-formed IP adress
    private boolean checkIp(String ip) throws UnknownHostException, IOException {
        if (!Pattern.matches("^(\\d{1,3}\\.){3}\\d{1,3}$", ip)) {
            return false;
        } // if
        for (String hex : ip.split(".")) {
            if (Integer.decode(hex) > 255) {
                return false;
            } // if
        } // for
        if (!InetAddress.getByName(ip).isReachable(10000)) {
            return false;
        } // if
        return true;
    } // boolean checkIp(String)

    private String parentDir(String realPath) {
        String[] dirs = realPath.split("/");
        String result = "";
        for (int i = 0; i + 1 < dirs.length; ++i) {
            result += dirs[i] + "/";
        }
        return result;
    }

    private void log(String message) {
        this.log.setValue(log.getValue() + message + "\n");
    }
}
