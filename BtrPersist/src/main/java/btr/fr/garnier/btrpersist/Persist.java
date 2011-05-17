/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import java.io.File;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

/**
 *
 * @author bobi
 */
public class Persist {

    private static File configFile;

    public static void setConfigFile(File configFile) {
        Persist.configFile = configFile;
    }

    public static void save(Object o) {
        SessionFactory sessionFactory = Persist.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.save(o);
        session.flush();
        session.close();
        sessionFactory.close();
    }

    public static List get(Class clazz) {
        return Persist.getCriteria(clazz).list();
    } // get(Class)

    public static List get(Class clazz, String orderField) {
        Criteria criteria = Persist.getCriteria(clazz);
        criteria.addOrder(Order.asc(orderField));
        return criteria.list();
    } // get(Class, String)

    private static SessionFactory getSessionFactory() {
        if (Persist.configFile == null) {
            return HibernateUtil.getSessionFactory();
        } // if
        return HibernateUtil.getSessionFactory(Persist.configFile);
    }

    private static Criteria getCriteria(Class clazz) {
        return Persist.getSessionFactory().openSession().createCriteria(clazz);
    }
}
