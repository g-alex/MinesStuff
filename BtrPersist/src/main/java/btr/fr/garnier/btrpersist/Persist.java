/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import java.io.File;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author bobi
 */
public class Persist {

    public static void save(Object o) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.save(o);
        session.flush();
        session.close();
        sessionFactory.close();
    }

    public static void save(Object o, File configFile) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(configFile);
        Session session = sessionFactory.openSession();
        session.save(o);
        session.flush();
        session.close();
        sessionFactory.close();
    }

    public static List get(Class clazz, File configFile) {
        return HibernateUtil.getSessionFactory(configFile).openSession().createCriteria(clazz).list();
    }

    public static List get(Class clazz) {
        return HibernateUtil.getSessionFactory().openSession().createCriteria(clazz).list();
    }
}
