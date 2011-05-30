/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.reports;

/**
 * Mapped entity class to Metric hibernate table.
 *
 * @author agarnier
 */
public class Metric {

    private String type;
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

    Metric(String type, String machine, double minWatt, double maxWatt, double avgWatt, double minCpu, double maxCpu, double avgCpu, double minRam, double maxRam, double avgRam) {
        this.type = type;
        this.machine = machine;
        this.minWatt = minWatt;
        this.maxWatt = maxWatt;
        this.avgWatt = avgWatt;
        this.minCpu = minCpu;
        this.maxCpu = maxCpu;
        this.avgCpu = avgCpu;
        this.minRam = minRam;
        this.maxRam = maxRam;
        this.avgRam = avgRam;
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
}
