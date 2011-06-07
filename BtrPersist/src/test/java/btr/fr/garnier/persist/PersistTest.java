/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author bobi
 */
public class PersistTest {

    @Test
    public void getClassTest() {
        Assert.assertTrue(Persist.get(Dummy.class).size() >= 0);
    }

    @Test
    public void saveFailTest() {
        try {
            Persist.save();
            Assert.fail("No object to save and no exception raised.");
        } catch (AssertionError e) {
            Assert.assertTrue(true);
        } // try
    }

    @Test
    public void deleteFailTest() {
        try {
            Persist.delete();
            Assert.fail("No object to delete and no exception raised.");
        } catch (AssertionError e) {
            Assert.assertTrue(true);
        } // try
    }

    @Test
    public void PersistTest() {
        int initSize = Persist.get(Dummy.class).size();
        Assert.assertTrue(initSize >= 0);
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        Assert.assertEquals(Persist.get(Dummy.class).size(), initSize + 1);
        Assert.assertTrue(Persist.get(Dummy.class).contains(dummy));
        Assert.assertFalse(Persist.get(Dummy.class, Restrictions.gt("id", dummy.getId())).contains(dummy));
        Persist.delete(dummy);
        Assert.assertEquals(Persist.get(Dummy.class).size(), initSize);
        Assert.assertFalse(Persist.get(Dummy.class).contains(dummy));
    }

    @Test
    public void multiplePersistTest() throws NoSuchFieldException {
        int initSize = Persist.get(Dummy.class).size();
        Assert.assertTrue(initSize >= 0);
        Dummy d1 = new Dummy(), d2 = new Dummy();
        Persist.save(d1, d2);
        Assert.assertEquals(Persist.get(Dummy.class).size(), initSize + 2);
        Integer i1 = null, i2 = null;
        List ascDummies = Persist.get(Dummy.class, Dummy.class.getDeclaredField("id"), Ordering.ASC);
        for (int i = 0; i < ascDummies.size(); ++i) {
            if (d1.equals((Dummy) ascDummies.get(i))) {
                Assert.assertNull(i2);
                i1 = i;
            } // if
            if (d2.equals((Dummy) ascDummies.get(i))) {
                Assert.assertNotNull(i1);
                i2 = i;
                break;
            } // if
        } // for
        Assert.assertTrue(i2 > i1);
        i1 = null;
        i2 = null;
        List descDummies = Persist.get(Dummy.class, Dummy.class.getDeclaredField("id"), Ordering.DESC);
        for (int i = 0; i < descDummies.size(); ++i) {
            if (d1.equals((Dummy) descDummies.get(i))) {
                Assert.assertNotNull(i2);
                i1 = i;
                break;
            } // if
            if (d2.equals((Dummy) descDummies.get(i))) {
                Assert.assertNull(i1);
                i2 = i;
            } // if
        } // for
        Assert.assertTrue(i1 > i2);
        Persist.delete(d1, d2);
        Assert.assertEquals(Persist.get(Dummy.class).size(), initSize);
        Assert.assertFalse(Persist.get(Dummy.class).contains(d1));
        Assert.assertFalse(Persist.get(Dummy.class).contains(d2));
    }

    @Test
    public void identSelectTest() throws NoSuchFieldException {
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.IDENT);
        Assert.assertTrue(Persist.select(Dummy.class, projections).contains(dummy.getId()));
    }

    @Test
    public void groupSelectTest() throws NoSuchFieldException {
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.GROUP);
        Assert.assertEquals(Persist.select(Dummy.class, projections).size(), Persist.get(Dummy.class).size());
    }

    @Test
    public void idGroupSelectTest() throws NoSuchFieldException {
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.IDGROUP);
        List dummiesIds = Persist.select(Dummy.class, projections);
        long id = dummy.getId();
        isSelected:
        {
            for (Object bob : dummiesIds) {
                Object[] toto = (Object[]) bob;
                if (((Long) toto[0]) == id) {
                    Assert.assertTrue(true);
                    break isSelected;
                } // if
            } // for
            Assert.fail("Dummy not properly selected");
        } // isSelected
        Assert.assertEquals(dummiesIds.size(), Persist.get(Dummy.class).size());
    }

    @Test
    public void minSelectTest() throws NoSuchFieldException {
        Dummy d1 = new Dummy(), d2 = new Dummy();
        Persist.save(d1, d2);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.MIN);
        long id = d2.getId(), min = (Long) Persist.select(Dummy.class, projections).get(0);
        Assert.assertTrue(min < id);
        Persist.delete(d2);
    }

    @Test
    public void maxSelectTest() throws NoSuchFieldException {
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.MAX);
        List objects = Persist.select(Dummy.class, projections);
        long id = dummy.getId(), max = (Long) objects.get(0);
        Assert.assertEquals(max, id);
        Persist.delete(dummy);
    }

    @Test
    public void avgSelectTest() throws NoSuchFieldException {
        Dummy dummy = new Dummy();
        Persist.save(dummy, new Dummy());
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.AVG);
        long id = dummy.getId();
        double avg = (Double) Persist.select(Dummy.class, projections).get(0);
        Assert.assertTrue(avg < id);
        Persist.delete(dummy);
    }

    @Test
    public void mmaSelectTest() throws NoSuchFieldException {
        Dummy d1 = new Dummy(), d2 = new Dummy(), d3 = new Dummy();
        Persist.save(d1, d2, d3);
        Field idField = Dummy.class.getDeclaredField("id");
        LinkedHashMap<Field, Selection> projections = new LinkedHashMap<Field, Selection>();
        projections.put(idField, Selection.MMA);
        List objects = Persist.select(Dummy.class, projections);
        long l = d3.getId(), min = (Long) ((Object[]) objects.get(0))[0],
                max = (Long) ((Object[]) objects.get(0))[1];
        double avg = ((Double) ((Object[]) objects.get(0))[2]);
        Assert.assertEquals(max, l);
        Assert.assertTrue(min < avg);
        Assert.assertTrue(avg < max);
        Persist.delete(d1, d2, d3);
    }
}
