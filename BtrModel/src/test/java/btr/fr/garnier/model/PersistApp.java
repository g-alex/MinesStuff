/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import java.util.ArrayList;
import java.util.List;
import btr.fr.garnier.persist.Persist;

/**
 *
 * @author agarnier
 */
public class PersistApp {

    public static void main(String[] args) {

        List<MachineConsumption> machineConsumptions = new ArrayList<MachineConsumption>();
        for (long time = 1303217634820L; time < 1303217634820L + 5000L * 1000L; time += 5000L) {
            machineConsumptions.add(new MachineConsumption(MachineType.SERVER, time, "bobi", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.SERVER, time, "bobu", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.VM, time, "toto", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.VM, time, "titi", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.VM, time, "tata", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.VM, time, "tete", genWatt(), genCpu(), genRam()));
            machineConsumptions.add(new MachineConsumption(MachineType.VM, time, "tutu", genWatt(), genCpu(), genRam()));
        } // for
        Persist.save(machineConsumptions.toArray());
    }

    private static double genWatt() {
        return Math.random() * 2D + 27D;
    }

    private static double genCpu() {
        return Math.random() * 100D;
    }

    private static double genRam() {
        return Math.random() * 30D + 240D;
    }
}
