/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.reports;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Mapped entity class to Metric hibernate table.
 *
 * @author agarnier
 */
@Entity
public class Metric implements Serializable {

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

    public void setAvgCpu(double avgCpu) {
        this.avgCpu = avgCpu;
    }

    public void setAvgRam(double avgRam) {
        this.avgRam = avgRam;
    }

    public void setAvgWatt(double avgWatt) {
        this.avgWatt = avgWatt;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setMaxCpu(double maxCpu) {
        this.maxCpu = maxCpu;
    }

    public void setMaxRam(double maxRam) {
        this.maxRam = maxRam;
    }

    public void setMaxWatt(double maxWatt) {
        this.maxWatt = maxWatt;
    }

    public void setMinCpu(double minCpu) {
        this.minCpu = minCpu;
    }

    public void setMinRam(double minRam) {
        this.minRam = minRam;
    }

    public void setMinWatt(double minWatt) {
        this.minWatt = minWatt;
    }

    public void setType(String type) {
        this.type = type;
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
