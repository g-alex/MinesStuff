/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * MachineConsumptions tests
 *
 * @author agarnier
 */
public class MachineConsumptionTest {

    long id = 1L;
    MachineType machineType = MachineType.SERVER;
    long time = 2L;
    String name = "nam";
    double watt = 2D;
    double cpu = 3D;
    double ram = 4D;

    @Test
    public void DefaultConstructorTest() {
        MachineConsumption machineConsumption = null;
        Assert.assertNull(machineConsumption);
        Assert.assertNotNull(new MachineConsumption());
    }

    @Test
    public void ConstructorTest() {
        Assert.assertNotNull(new MachineConsumption(machineType, time, name, watt, cpu, ram));
    }

    @Test
    public void gettersTest() {
        MachineConsumption machineConsumption = new MachineConsumption(machineType, time, name, watt, cpu, ram);
        Assert.assertEquals(machineConsumption.getId(), 0L);
        Assert.assertEquals(machineType, machineConsumption.getCategory());
        Assert.assertEquals(time, machineConsumption.getTime());
        Assert.assertNotNull(name);
        Assert.assertEquals(name, machineConsumption.getName());
        Assert.assertEquals(watt, machineConsumption.getWatt(), 0D);
        Assert.assertEquals(cpu, machineConsumption.getCpu(), 0D);
        Assert.assertEquals(ram, machineConsumption.getRam(), 0D);
    }

    @Test
    public void settersTest() {
        MachineConsumption machineConsumption = new MachineConsumption();
        machineConsumption.setId(id);
        machineConsumption.setCategory(machineType);
        machineConsumption.setTime(time);
        machineConsumption.setName(name);
        machineConsumption.setWatt(watt);
        machineConsumption.setCpu(cpu);
        machineConsumption.setRam(ram);
        Assert.assertEquals(machineType, machineConsumption.getCategory());
        Assert.assertEquals(time, machineConsumption.getTime());
        Assert.assertNotNull(name);
        Assert.assertEquals(name, machineConsumption.getName());
        Assert.assertEquals(watt, machineConsumption.getWatt(), 0D);
        Assert.assertEquals(cpu, machineConsumption.getCpu(), 0D);
        Assert.assertEquals(ram, machineConsumption.getRam(), 0D);
    }

    @Test
    public void equalsTest() {
        MachineConsumption mc1 = new MachineConsumption(), mc2 = new MachineConsumption();
        Assert.assertFalse(mc1.equals(null));
        Assert.assertFalse(mc1.equals(new Object()));
        Assert.assertTrue(mc1.equals(mc1));
        Assert.assertTrue(mc1.equals(mc2));
        mc1.setName(name);
        Assert.assertFalse(mc1.equals(mc2));
        mc2.setName(name);
        Assert.assertTrue(mc1.equals(mc2));
        mc1.setTime(time);
        Assert.assertFalse(mc1.equals(mc2));
        mc2.setTime(time);
        Assert.assertTrue(mc1.equals(mc2));
        mc1.setCategory(machineType);
        Assert.assertFalse(mc1.equals(mc2));
    }

    @Test
    public void hashCodeTest() {
        MachineConsumption mc1 = new MachineConsumption(), mc2 = new MachineConsumption();
        Assert.assertEquals(mc1.hashCode(), mc2.hashCode());
        mc1.setCategory(machineType);
        Assert.assertTrue(mc1.hashCode() != mc2.hashCode());
        mc2.setCategory(machineType);
        Assert.assertEquals(mc1.hashCode(), mc2.hashCode());
        mc1.setName(name);
        Assert.assertTrue(mc1.hashCode() != mc2.hashCode());
    }

    @Test
    public void StringTest() {
        MachineConsumption mc1 = new MachineConsumption(), mc2 = new MachineConsumption();
        Assert.assertEquals(mc1.toString(),mc2.toString());
    }
}
