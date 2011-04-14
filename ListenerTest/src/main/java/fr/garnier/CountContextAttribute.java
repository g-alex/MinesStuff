/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bobi
 */
public class CountContextAttribute {

    private static Map<String, Object> attributes = new HashMap<String, Object>();
    private static int count = 0;

    public static void incremente() {
        ++count;
    }

    public static int getCount() {
        return count;
    }

    public static Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public static Object setAttribute(String name, Object value) {
        return attributes.put(name, value);
    }

    public static Map<String, Object> getAttributes() {
        return attributes;
    }
}
