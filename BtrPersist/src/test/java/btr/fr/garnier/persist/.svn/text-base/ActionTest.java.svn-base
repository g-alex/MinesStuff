/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Test;

/**
 *
 * @author agarnier
 */
public class ActionTest {

    @Test
    public void actionsTest() {
        Dummy dummy = new Dummy();
        Session session = new AnnotationConfiguration().addAnnotatedClass(Dummy.class).configure().buildSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Dummy.class);
        int initSize = criteria.list().size();
        Action.SAVE.apply(session, dummy);
        assert criteria.list().size() == initSize + 1;
        Action.DELETE.apply(session, dummy);
        session.flush();
        assert criteria.list().size() == initSize;
        session.close();
    }
}
