/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import java.lang.reflect.Field;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * Abstracted hibernate-based persistence interface.
 *
 * @see java.lang.Class
 * @see java.lang.reflect.Field
 * @see java.util.List
 * @author agarnier
 */
public class Persist {

    /**
     * Persist given object.
     * 
     * @param object Object to persist.
     */
    public static void save(Object object) {
        Persist.append(object, Action.SAVE);
    } // static void save(Object)

    /**
     * Delete persisted object.
     *
     * @param object Object to delete.
     */
    public static void delete(Object object) {
        Persist.append(object, Action.DELETE);
    } // static void delete(Object)

    /**
     * Get persisted objects of the given class.
     *
     * @param clazz Class of the objects to get.
     * @return List of persisted clazz objects.
     */
    public static List get(Class clazz) {
        SessionFactory sessionFactory = Persist.getSessionFactory(clazz);
        Session session = sessionFactory.openSession();
        List result = session.createCriteria(clazz).list();
        session.close();
        sessionFactory.close();
        return result;
    } // get(Class)

    /**
     * Get an ordered list of objects of the given class.
     *
     * @param clazz Class of the objects to get.
     * @param field Ordering field of the list.
     * @return Ordered list of persisted clazz objects.
     */
    public static List get(Class clazz, Field field) {
        SessionFactory sessionFactory = Persist.getSessionFactory(clazz);
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.addOrder(Order.asc(field.getName()));
        List result = criteria.list();
        session.close();
        sessionFactory.close();
        return result;
    } // get(Class, String)

    public static PersistSession openSession(Class clazz) {
        return new PersistSession(clazz);
    }

    private static void append(Object object, Action action) {
        SessionFactory sessionFactory = Persist.getSessionFactory(object.getClass());
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        switch (action) {
            case SAVE:
                session.save(object);
                break;
            case DELETE:
                session.delete(object);
                break;
        } // switch
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    static SessionFactory getSessionFactory(Class clazz) {
        return new AnnotationConfiguration().addAnnotatedClass(clazz).configure().buildSessionFactory();
    }
}
