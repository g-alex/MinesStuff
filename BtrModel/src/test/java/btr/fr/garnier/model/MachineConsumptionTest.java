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

    @Test
    public void DefaultConstructorTest() {
        MachineConsumption machineConsumption = null;
        Assert.assertNull(machineConsumption);
        Assert.assertNotNull(new MachineConsumption());
    }

    @Test
    public void ConstructorTest() {
        MachineType machineType = MachineType.SERVER;
        long time = 1L;
        String nom = "";
        double watt = 2D;
        double cpu = 3D;
        double ram = 4D;
        Assert.assertNotNull(new MachineConsumption(machineType, time, nom, watt, cpu, ram));
    }

    @Test
    public void gettersTest() {
        MachineType machineType = MachineType.SERVER;
        long time = 1L;
        String nom = "";
        double watt = 2D;
        double cpu = 3D;
        double ram = 4D;
        MachineConsumption machineConsumption = new MachineConsumption(machineType, time, nom, watt, cpu, ram);
        Assert.assertEquals(machineType, machineConsumption.getCategory());
        Assert.assertEquals(time, machineConsumption.getTime());
        Assert.assertNotNull(nom);
        Assert.assertEquals(nom, machineConsumption.getNom());
        Assert.assertEquals(watt, machineConsumption.getWatt(), 0D);
        Assert.assertEquals(cpu, machineConsumption.getCpu(), 0D);
        Assert.assertEquals(ram, machineConsumption.getRam(), 0D);
    }

    @Test
    public void settersTest() {
        MachineType machineType = MachineType.SERVER;
        long time = 1L;
        String nom = "";
        double watt = 2D;
        double cpu = 3D;
        double ram = 4D;
        MachineConsumption machineConsumption = new MachineConsumption();
        machineConsumption.setCategory(machineType);
        machineConsumption.setTime(time);
        machineConsumption.setNom(nom);
        machineConsumption.setWatt(watt);
        machineConsumption.setCpu(cpu);
        machineConsumption.setRam(ram);
        Assert.assertEquals(machineType, machineConsumption.getCategory());
        Assert.assertEquals(time, machineConsumption.getTime());
        Assert.assertNotNull(nom);
        Assert.assertEquals(nom, machineConsumption.getNom());
        Assert.assertEquals(watt, machineConsumption.getWatt(), 0D);
        Assert.assertEquals(cpu, machineConsumption.getCpu(), 0D);
        Assert.assertEquals(ram, machineConsumption.getRam(), 0D);
    }
}
