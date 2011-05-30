/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import org.hibernate.Session;

/**
 * Hibernate amending actions.
 *
 * @author agarnier
 */
enum Action {

    SAVE {

        void doIt(Session session, Object object) {
            session.save(object);
        }
    }, DELETE {

        void doIt(Session session, Object object) {
            session.delete(object);
        }
    };

    abstract void doIt(Session session, Object object);
}
