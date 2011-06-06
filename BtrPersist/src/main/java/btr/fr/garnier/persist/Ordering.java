/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import java.lang.reflect.Field;
import org.hibernate.criterion.Order;

/**
 *
 * @author agarnier
 */
public enum Ordering {

    ASC {

        Order get(Field field) {
            return Order.asc(field.getName());
        } // ASC: Order get(Field)
    }, DESC {

        Order get(Field field) {
            return Order.desc(field.getName());
        } // DESC: Order get(Field)
    };

    abstract Order get(Field field);
}
