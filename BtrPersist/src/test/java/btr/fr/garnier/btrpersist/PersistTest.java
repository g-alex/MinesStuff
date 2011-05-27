/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import btr.fr.garnier.persist.Persist;
import org.junit.Test;

/**
 *
 * @author bobi
 */
public class PersistTest {

    @Test
    public void getClassTest() {
        assert Persist.get(Dummy.class).size() >= 0;
    }

    @Test
    public void PersistTest() {
        int initSize = Persist.get(Dummy.class).size();
        assert initSize >= 0;
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        assert Persist.get(Dummy.class).size() == initSize + 1;
        assert Persist.get(Dummy.class).contains(dummy);
        Persist.delete(dummy);
        assert Persist.get(Dummy.class).size() == initSize;
        assert !Persist.get(Dummy.class).contains(dummy);
    }
}
