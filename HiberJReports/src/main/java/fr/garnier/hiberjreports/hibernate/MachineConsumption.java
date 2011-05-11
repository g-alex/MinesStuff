/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.hiberjreports.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bobi
 */
@Entity
@Table(name = "metrics")
public class MachineConsumption {

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

    public void setAvgCpu(double avgCpu) {
        this.avgCpu = avgCpu;
    }

    public double getAvgRam() {
        return avgRam;
    }

    public void setAvgRam(double avgRam) {
        this.avgRam = avgRam;
    }

    public double getAvgWatt() {
        return avgWatt;
    }

    public void setAvgWatt(double avgWatt) {
        this.avgWatt = avgWatt;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public double getMaxCpu() {
        return maxCpu;
    }

    public void setMaxCpu(double maxCpu) {
        this.maxCpu = maxCpu;
    }

    public double getMaxRam() {
        return maxRam;
    }

    public void setMaxRam(double maxRam) {
        this.maxRam = maxRam;
    }

    public double getMaxWatt() {
        return maxWatt;
    }

    public void setMaxWatt(double maxWatt) {
        this.maxWatt = maxWatt;
    }

    public double getMinCpu() {
        return minCpu;
    }

    public void setMinCpu(double minCpu) {
        this.minCpu = minCpu;
    }

    public double getMinRam() {
        return minRam;
    }

    public void setMinRam(double minRam) {
        this.minRam = minRam;
    }

    public double getMinWatt() {
        return minWatt;
    }

    public void setMinWatt(double minWatt) {
        this.minWatt = minWatt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MachineConsumption{" + "type=" + type + "machine=" + machine + "minWatt=" + minWatt + "maxWatt=" + maxWatt + "avgWatt=" + avgWatt + "minCpu=" + minCpu + "maxCpu=" + maxCpu + "avgCpu=" + avgCpu + "minRam=" + minRam + "maxRam=" + maxRam + "avgRam=" + avgRam + '}';
    }
}
