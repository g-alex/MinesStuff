/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author agarnier
 */
public class MetricTest {

    String type = "";
    String machine = "";
    double minWatt = 0D;
    double maxWatt = 2D;
    double avgWatt = 1D;
    double minCpu = 3D;
    double maxCpu = 5D;
    double avgCpu = 4D;
    double minRam = 6D;
    double maxRam = 8D;
    double avgRam = 7D;

    @Test
    public void ConstructorTest() {
        Metric metric = null;
        Assert.assertNull(metric);
        Assert.assertNotNull(new Metric(type, machine, minWatt, maxWatt, avgWatt, minCpu, maxCpu, avgCpu, minRam, maxRam, avgRam));
    }

    @Test
    public void gettersTest() {
        Metric metric = new Metric(type, machine, minWatt, maxWatt, avgWatt, minCpu, maxCpu, avgCpu, minRam, maxRam, avgRam);
        Assert.assertNotNull(metric.getType());
        Assert.assertEquals(type, metric.getType());
        Assert.assertNotNull(metric.getMachine());
        Assert.assertEquals(machine, metric.getMachine());
        Assert.assertEquals(minWatt, metric.getMinWatt(), 0D);
        Assert.assertEquals(maxWatt, metric.getMaxWatt(), 0D);
        Assert.assertEquals(avgWatt, metric.getAvgWatt(), 0D);
        Assert.assertEquals(minCpu, metric.getMinCpu(), 0D);
        Assert.assertEquals(maxCpu, metric.getMaxCpu(), 0D);
        Assert.assertEquals(avgCpu, metric.getAvgCpu(), 0D);
        Assert.assertEquals(minRam, metric.getMinRam(), 0D);
        Assert.assertEquals(maxRam, metric.getMaxRam(), 0D);
        Assert.assertEquals(avgRam, metric.getAvgRam(), 0D);
    }
}
