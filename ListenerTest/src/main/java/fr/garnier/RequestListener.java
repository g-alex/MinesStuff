/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 *
 * @author bobi
 */
public class RequestListener implements ServletRequestListener {

    public void requestDestroyed(ServletRequestEvent event) {
        CountRequest.incremente();
    }

    public void requestInitialized(ServletRequestEvent event) {
    }
}
