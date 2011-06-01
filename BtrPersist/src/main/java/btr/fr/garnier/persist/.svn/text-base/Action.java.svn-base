/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import org.hibernate.Session;

/**
 * Hibernate amending operations.
 *
 * @author agarnier
 */
enum Action {

    SAVE {

        void apply(Session session, Object object) {
            session.save(object);
        }
    }, DELETE {

        void apply(Session session, Object object) {
            session.delete(object);
        }
    };

    /**
     * Apply action.
     *
     * @param session Session where to apply the action.
     * @param object Object on which the action is applied.
     */
    abstract void apply(Session session, Object object);
}
