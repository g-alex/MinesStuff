/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

/**
 *
 * @author bobi
 */
public class CountContext {

    private static int count = 0;

    public static void incremente() {
        ++count;
    }

    public static int getCount() {
        return count;
    }
}
