package fr.garnier.persistest;

import btr.fr.garnier.btrpersist.Persist;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        File configFile = new File("src/main/resources/hibernate.cfg.xml");
        Persist.save(new Dummy(), configFile);
        for (Object o : Persist.get(Dummy.class, configFile)) {
            System.out.println((Dummy) o);
        } // for
    } // static void main(String[])
} // class App

