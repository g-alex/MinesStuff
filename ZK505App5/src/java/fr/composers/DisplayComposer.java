/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.composers;

import fr.xml.XMLManagement;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

/**
 *
 * @author bobi
 */
public class DisplayComposer extends GenericForwardComposer {

    Window win;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        win.setTitle(XMLManagement.getIp(XMLManagement.openXML(XMLManagement.CATALINA_HOME + "/conf/Catalina/localhost/guacamole.xml")));
    }
}
