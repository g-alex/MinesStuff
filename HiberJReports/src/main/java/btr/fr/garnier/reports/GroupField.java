/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.reports;

/**
 * Grouping fields of metrics.
 *
 * @author agarnier
 */
enum GroupField {

    CATEG {

        String categoryToType(String category) {
            return "All " + category + "s";
        } // CATEG: String categoryToType(String)

        String nameToMachine(String name) {
            return name + "s";
        } // CATEG: String nameToMachine(String)
    }, NAME {

        String categoryToType(String category) {
            return "Each " + category;
        } // NAME: String categoryToType(String)

        String nameToMachine(String name) {
            return name;
        } // NAME: String nameToMachine(String)
    };

    /**
     * Parse MachineConsumption category field value into metrics type field.
     *
     * @param category Origin MachineConsumption field.
     * @return Target metrics type field.
     */
    abstract String categoryToType(String category);

    /**
     * Parse MachineConsumption name field value into metrics machine field.
     *
     * @param category Origin MachineConsumption field.
     * @return Target metrics machine field.
     */
    abstract String nameToMachine(String name);
}
