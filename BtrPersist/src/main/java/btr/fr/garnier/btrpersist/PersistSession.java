/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author agarnier
 */
public class PersistSession {

    private Class clazz;
    private SessionFactory sessionFactory;
    private Session session;

    PersistSession(Class clazz) {
        this.clazz = clazz;
        this.sessionFactory = Persist.getSessionFactory(this.clazz);
        this.session = this.sessionFactory.openSession();
    }

    public void close() {
        this.session.close();
        this.sessionFactory.close();
    }

    public Criteria createCriteria() {
        return this.session.createCriteria(this.clazz);
    }
}
