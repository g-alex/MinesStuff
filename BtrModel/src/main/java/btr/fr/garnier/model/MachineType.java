/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

/**
 * Enumeration of different machines types.
 *
 * @author agarnier
 */
public enum MachineType {

    SERVER {

        @Override
        public String toString() {
            return "Server";
        }
    }, VM;
}