/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.persistest;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author bobi
 */
@Entity
public class Dummy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public String toString() {
        return "Dummy{" + "id=" + id + '}';
    }
}
