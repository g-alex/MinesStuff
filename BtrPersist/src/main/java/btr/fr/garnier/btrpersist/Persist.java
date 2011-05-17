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
 * Abstracted hibernate-based persistence interface.
 *
 * @see java.io.File
 * @see java.util.List
 * @see org.hibernate.Criteria
 * @author agarnier
 */
public class Persist {

    private static File configFile;

    /**
     * Set hibernate configuration file.
     *
     * @param configFile Hibernate configuration file.
     */
    public static void setConfigFile(File configFile) {
        Persist.configFile = configFile;
    } // static void setConfigFile(File)

    /**
     * Persist given object.
     * 
     * @param object Object to persist.
     */
    public static void save(Object object) {
        SessionFactory sessionFactory = Persist.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.save(object);
        session.flush();
        session.close();
        sessionFactory.close();
    } // static void save(Object)

    /**
     * Get persisted objects of the given class.
     *
     * @param clazz Class of the objects to get.
     * @return List of persisted clazz objects.
     */
    public static List get(Class clazz) {
        return Persist.getCriteria(clazz).list();
    } // get(Class)

    /**
     * Get an ordered list of objects of the given class.
     *
     * @param clazz Class of the objects to get.
     * @param orderField Ordering field of the list.
     * @return Ordered list of persisted clazz objects.
     */
    public static List get(Class clazz, String orderField) {
        Criteria criteria = Persist.getCriteria(clazz);
        criteria.addOrder(Order.asc(orderField));
        return criteria.list();
    } // get(Class, String)

    /**
     * Get session factory (default one if configFile is not setted).
     * 
     * @return session factory.
     */
    private static SessionFactory getSessionFactory() {
        if (Persist.configFile == null) {
            return HibernateUtil.getSessionFactory();
        } // if
        return HibernateUtil.getSessionFactory(Persist.configFile);
    } // static SessionFactory getSessionFactory()

    /**
     * Get criteria on the given class.
     *
     * @param clazz Class aimed by criteria.
     * @return Criteria on clazz.
     */
    private static Criteria getCriteria(Class clazz) {
        return Persist.getSessionFactory().openSession().createCriteria(clazz);
    } // static Criteria getCriteria(Class)
}
