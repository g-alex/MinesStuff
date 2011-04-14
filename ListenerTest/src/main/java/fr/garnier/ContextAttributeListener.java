/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 *
 * @author bobi
 */
public class ContextAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        CountContextAttribute.incremente();
        CountContextAttribute.setAttribute(event.getName(), event.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        CountContextAttribute.removeAttribute(event.getName());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        CountContextAttribute.setAttribute(event.getName(), event.getValue());
    }
}
