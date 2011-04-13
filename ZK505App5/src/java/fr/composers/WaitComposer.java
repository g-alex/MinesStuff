/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.composers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author bobi
 */
public class WaitComposer extends GenericForwardComposer {

    @Override
    public void doFinally() throws Exception {
        super.doFinally();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitComposer.class.getName()).log(Level.SEVERE, null, ex);
        } // catch
        Executions.sendRedirect("/display.zul");
    }
}
