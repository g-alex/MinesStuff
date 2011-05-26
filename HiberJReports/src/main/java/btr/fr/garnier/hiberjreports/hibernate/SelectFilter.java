/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.hiberjreports.hibernate;

/**
 *
 * @author bobi
 */
public enum SelectFilter {

    ALL {

        public String typeToString(String type) {
            return "ALL " + type + "S";
        }

        public String machineToString(String machine) {
            return machine + "S";
        }
    }, EACH {

        public String typeToString(String type) {
            return "EACH " + type;
        }

        public String machineToString(String machine) {
            return machine;
        }
    };

    public abstract String typeToString(String type);

    public abstract String machineToString(String machine);
}
