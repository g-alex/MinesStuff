package fr.garnier.persistest;

import btr.fr.garnier.btrpersist.Persist;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        for (Object o : Persist.get(MachineConsumption.class)) {
            System.out.println((MachineConsumption) o);
        } // for
    } // static void main(String[])
} // class App

