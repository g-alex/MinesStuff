/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.reports;

/**
 *
 * @author agarnier
 */
enum GroupField {

    CATEG {

        String categoryToType(String category) {
            return "ALL " + category + "S";
        } // CATEG: String categoryToType(String)

        String nameToMachine(String name) {
            return name + "S";
        } // CATEG: String nameToMachine(String)
    }, NAME {

        String categoryToType(String category) {
            return "EACH " + category;
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
