package fr.garnier.persistest;

import btr.fr.garnier.btrpersist.Persist;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Persist.setConfigFile(new File("src/main/resources/hibernate.cfg.xml"));

        for (Object o : Persist.get(MachineConsumption.class)) {
            System.out.println((MachineConsumption) o);
        } // for
    } // static void main(String[])
} // class App

