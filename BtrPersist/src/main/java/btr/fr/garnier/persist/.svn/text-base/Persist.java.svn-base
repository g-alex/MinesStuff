/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
     * Persist given objects.
     *
     * @param objects Objects to persist.
     */
    public static void save(Object... objects) {
        Persist.append(Action.SAVE, objects);
    } // static void save(Object)

    /**
     * Delete persisted objects.
     *
     * @param objects Objects to delete.
     */
    public static void delete(Object... objects) {
        Persist.append(Action.DELETE, objects);
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
    } // static List get(Class)

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
    } // static List get(Class, String)

    /**
     * Select given fields on objects of the given class.
     *
     * @param clazz Class to select on.
     * @param fields Maps of fields and operation done on it.
     * @return A list of fields selected on persisted clazz objects.
     */
    public static List select(Class clazz, Map<Field, Selection> fields) {
        SessionFactory sessionFactory = Persist.getSessionFactory(clazz);
        Session session = sessionFactory.openSession();
        ProjectionList projectionList = Projections.projectionList();
        for (Map.Entry<Field, Selection> entry : fields.entrySet()) {
            projectionList = entry.getValue().getProjection(projectionList, entry.getKey());
        } // for
        Criteria criteria = session.createCriteria(clazz).setProjection(projectionList);
        List result = criteria.list();
        session.close();
        sessionFactory.close();
        return result;
    } // static List select(Class, Map<Field, Selection>)

    private static void append(Action action, Object... objects) {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        boolean set = false;
        for (Object object : objects) {
            if (!set) {
                sessionFactory = Persist.getSessionFactory(object.getClass());
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                set = true;
            } // if
            action.apply(session, object);
        } // for
        transaction.commit();
        session.close();
        sessionFactory.close();
    } // static void append(Object, Action)

    private static SessionFactory getSessionFactory(Class clazz) {
        return new AnnotationConfiguration().addAnnotatedClass(clazz).configure().buildSessionFactory();
    } // static SessionFactory getSessionFactory(Class)
}
