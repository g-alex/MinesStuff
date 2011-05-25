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

    private static final long serialVersionUID = 1L;
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

    public void setAttributes(Object[] objects) {
        try {
            this.type = objects[0].toString();
            this.machine = objects[1].toString();
            this.minWatt = (Double) objects[2];
            this.maxWatt = (Double) objects[3];
            this.avgWatt = (Double) objects[4];
            this.minCpu = (Double) objects[5];
            this.maxCpu = (Double) objects[6];
            this.avgCpu = (Double) objects[7];
            this.minRam = (Double) objects[8];
            this.maxRam = (Double) objects[9];
            this.avgRam = (Double) objects[10];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

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
