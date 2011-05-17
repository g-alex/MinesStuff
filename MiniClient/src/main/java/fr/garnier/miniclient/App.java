package fr.garnier.miniclient;

import btr.fr.garnier.btrpersist.Persist;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Persist.setConfigFile(new File("src/main/resources/hibernate.cfg.xml"));
        for (Object o : Persist.get(Metrics.class, "type")) {
            System.out.println((Metrics) o);
        }
    }
}
