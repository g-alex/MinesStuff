/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

/**
 * Grouping fields of metrics.
 *
 * @author agarnier
 */
enum GroupField {

    CATEG {

        String typeToType(String category) {
            return "All " + category + "s";
        } // CATEG: String typeToType(String)

        @Override
        String nameToMachine(String name) {
            return name + "s";
        } // CATEG: String nameToMachine(String)
    }, NAME {

        String typeToType(String category) {
            return "Each " + category;
        } // NAME: String typeToType(String)
    };

    /**
     * Parse MachineConsumption category field value into metrics type field.
     *
     * @param category Origin MachineConsumption field.
     * @return Target metrics type field.
     */
    abstract String typeToType(String category);

    /**
     * Parse MachineConsumption name field value into metrics machine field.
     *
     * @param category Origin MachineConsumption field.
     * @return Target metrics machine field.
     */
    String nameToMachine(String name) {
        return name;
    } // String typeToType(String)
}
