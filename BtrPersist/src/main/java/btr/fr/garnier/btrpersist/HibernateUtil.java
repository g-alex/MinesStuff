/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import java.io.File;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author agarnier
 */
class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Get default session factory.
     *
     * @return Default session factory.
     */
    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Get session factory based on given hibernate configuration file.
     *
     * @param configFile Hibernate configuration file.
     * @return configFile based session factory.
     */
    static SessionFactory getSessionFactory(File configFile) {
        return new AnnotationConfiguration().configure(configFile).buildSessionFactory();
    }
}
