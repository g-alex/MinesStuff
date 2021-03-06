/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.composers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

/**
 *
 * @author agarnier
 */
public class IndexComposer extends GenericForwardComposer {

    private Map<String, String> ips;
    Listbox vncIp;
    Listitem noChoice;
    Button goVnc;
    Iframe guacamole;
    Textbox debug;

    public IndexComposer() {
        super();

        this.ips = new HashMap<String, String>();
        this.ips.put("Georg@172.28.2.197", "172.28.2.197");
        this.ips.put("Mike@192.168.122.220", "192.168.122.220");
        this.ips.put("VraiTet3@192.168.122.164", "192.168.122.164");
        this.ips.put("KVM@localhost", "127.0.0.1");
    } // IndexComposer()

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        for (Map.Entry<String, String> e : this.ips.entrySet()) {
            this.vncIp.appendItem(e.getKey(), e.getValue());
        } // for
        debug.setValue(this.session.getLocalName());
    } // void doAfterCompose(Component)

    public void onClick$goVnc(Event event) throws UnknownHostException, IOException {
        String ip = (String) this.vncIp.getSelectedItem().getValue();
        if (this.checkIp(ip)) {
            ((ServletContext) this.application.getNativeContext()).getContext("/guacamole").setAttribute("host", ip);
            ((ServletContext) this.application.getNativeContext()).getContext("/guacamole").setAttribute("port", 5901);
            if (this.guacamole.getSrc() == null) {
                String localAddr = this.execution.getLocalAddr();
                localAddr = localAddr.contains(":") ? "[" + localAddr + "]" : localAddr;
                this.guacamole.setSrc("http://" + localAddr + ":8080/guacamole");
            } // if
        } else {
            this.guacamole.setSrc(null);
        } // else
        this.guacamole.invalidate();
    } // void setXML(String)

    public void onClick$reset(Event event) {
        vncIp.setSelectedItem(noChoice);
        ((ServletContext) this.application.getNativeContext()).getContext("/guacamole").removeAttribute("host");
        this.guacamole.setSrc(null);
        this.guacamole.invalidate();
    }

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
}
