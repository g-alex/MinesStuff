/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

/**
 * Metrics on machine consumptions, to be parsed in JasperReports.
 * JasperReports need the getters, thus the class, to be public, so they are.
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

    /**
     * Setting constructor
     *
     * @param type Machine category (Server, VM, etc)
     * @param machine Machine name
     * @param minWatt Minimum watt consumption value
     * @param maxWatt Maximum watt consumption value
     * @param avgWatt Average watt consumption value
     * @param minCpu Minimum CPU consumption value
     * @param maxCpu Maximum CPU consumption value
     * @param avgCpu Average CPU consumption value
     * @param minRam Minimum RAM consumption value
     * @param maxRam Maximum RAM consumption value
     * @param avgRam Average RAM consumption value
     */
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
        return this.avgCpu;
    }

    public double getAvgRam() {
        return this.avgRam;
    }

    public double getAvgWatt() {
        return this.avgWatt;
    }

    public String getMachine() {
        return this.machine;
    }

    public double getMaxCpu() {
        return this.maxCpu;
    }

    public double getMaxRam() {
        return this.maxRam;
    }

    public double getMaxWatt() {
        return this.maxWatt;
    }

    public double getMinCpu() {
        return this.minCpu;
    }

    public double getMinRam() {
        return this.minRam;
    }

    public double getMinWatt() {
        return this.minWatt;
    }

    public String getType() {
        return this.type;
    }
}
