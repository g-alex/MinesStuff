/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import btr.fr.garnier.persist.Persist;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author agarnier
 */
public class ModelTest {

    @Test
    public void persistConsumptionsTest() {
        Model.persistConsumptions(MachineType.SERVER, 1L, "", 1D, 2D, 3D);
        MachineConsumption machineConsumption = new MachineConsumption(MachineType.SERVER, 1L, "", 1D, 2D, 3D);
        Assert.assertTrue(Persist.get(MachineConsumption.class).contains(machineConsumption));
        Persist.delete(machineConsumption);
    }

    @Test
    public void getPersistedMetricsTest() throws NoSuchFieldException {
        Assert.assertTrue(Model.getPersistedMetrics().size() >= 4);
    }
}
