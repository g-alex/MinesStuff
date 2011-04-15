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

/**
 *
 * @author agarnier
 */
public class IndexComposer extends GenericForwardComposer {

    private Map<String, String> ips;
    Listbox vncIp;
    Button goVnc;
    Iframe guacamole;

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
    } // void doAfterCompose(Component)

    public void onClick$goVnc(Event event) throws UnknownHostException, IOException {
        String ip = (String) this.vncIp.getSelectedItem().getValue();
        if (this.checkIp(ip)) {
            ((ServletContext) this.application.getNativeContext()).getContext("/guacadev").setAttribute("host", ip);
            if (this.guacamole.getSrc() == null) {
                this.guacamole.setSrc("http://localhost:8080/guacadev");
            } // if
            this.guacamole.invalidate();
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
}
