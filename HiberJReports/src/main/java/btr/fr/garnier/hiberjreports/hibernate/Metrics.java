/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.hiberjreports.hibernate;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Mapped entity class to Metrics hibernate table.
 *
 * @author agarnier
 */
@Entity
public class Metrics implements Serializable {

    private String type;
    @Id
    private String machine;
    private double minWatt;
    private double maxWatt;
    private double avgWatt;
    private double minCpu;
    private double maxCpu;
    private double avgCpu;
    private double minRam;
    private double maxRam;
    private double avgRam;

    public double getAvgCpu() {
        return avgCpu;
    }

    public double getAvgRam() {
        return avgRam;
    }

    public double getAvgWatt() {
        return avgWatt;
    }

    public String getMachine() {
        return machine;
    }

    public double getMaxCpu() {
        return maxCpu;
    }

    public double getMaxRam() {
        return maxRam;
    }

    public double getMaxWatt() {
        return maxWatt;
    }

    public double getMinCpu() {
        return minCpu;
    }

    public double getMinRam() {
        return minRam;
    }

    public double getMinWatt() {
        return minWatt;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MachineConsumption{" + "type=" + type + ", machine=" + machine
                + ", minWatt=" + minWatt + ", maxWatt=" + maxWatt
                + ", avgWatt=" + avgWatt + ", minCpu=" + minCpu + ", maxCpu="
                + maxCpu + ", avgCpu=" + avgCpu + ", minRam=" + minRam
                + ", maxRam=" + maxRam + ", avgRam=" + avgRam + '}';
    }
}
