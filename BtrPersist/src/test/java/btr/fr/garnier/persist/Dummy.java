/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bobi
 */
@Entity
public class Dummy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return this.id;
    } // long getId()

    @Override
    public String toString() {
        return "Dummy{" + "id=" + this.id + '}';
    } // String toString()

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } // if
        if (getClass() != object.getClass()) {
            return false;
        } // if
        final Dummy dummy = (Dummy) object;
        if (this.id != dummy.id) {
            return false;
        } // if
        return true;
    } // boolean equals(Object)

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    } // int hashCode()
}
