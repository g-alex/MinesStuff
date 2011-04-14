/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

import javax.servlet.ServletContext;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

/**
 *
 * @author bobi
 */
public class DistantAttribute extends GenericForwardComposer {

    Textbox name;
    Textbox value;

    public void onClick$setAttr() {
        ((ServletContext) this.application.getNativeContext()).getContext("/ListenerTest").setAttribute(name.getValue(), value.getValue());
        ((ServletContext) this.application.getNativeContext()).getContext("/NoListenerTest").setAttribute(name.getValue(), value.getValue());
        ((ServletContext) this.application.getNativeContext()).getContext("/guacadev").setAttribute(name.getValue(), value.getValue());
        name.setValue(null);
        value.setValue(null);
    }
}
