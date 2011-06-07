/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Persistable class representing machines consumptions.
 *
 * @author agarnier
 */
@Entity
public class MachineConsumption implements Serializable {

    private static final long serialVersionUID = 1L;
    // Persisted attributes
    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private MachineType type;
    private long timeAgo; // in a galaxy far far away
    private String name;
    private double watt;
    private double cpu;
    private double ram;

    /**
     * Default constructor, required by javax.persistence.Entity
     */
    public MachineConsumption() {
        super();
    }

    /**
     * Setting constructor
     *
     * @param category Machine category (Server, VM, etc)
     * @param time Data timestamp
     * @param name Machine name
     * @param watt Watt consumption value
     * @param cpu CPU consumption value
     * @param ram RAM consumption value
     */
    public MachineConsumption(MachineType category, long time, String name, double watt, double cpu, double ram) {
        this.type = category;
        this.timeAgo = time;
        this.name = name;
        this.watt = watt;
        this.cpu = cpu;
        this.ram = ram;
    }

    public MachineType getCategory() {
        return this.type;
    }

    public double getCpu() {
        return this.cpu;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public double getRam() {
        return this.ram;
    }

    public long getTime() {
        return this.timeAgo;
    }

    public double getWatt() {
        return this.watt;
    }

    public void setCategory(MachineType category) {
        this.type = category;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public void setTime(long time) {
        this.timeAgo = time;
    }

    public void setWatt(double watt) {
        this.watt = watt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MachineConsumption other = (MachineConsumption) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.timeAgo != other.timeAgo) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 37 * hash + (int) (this.timeAgo ^ (this.timeAgo >>> 32));
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Mesures{" + "category=" + type + ", time=" + timeAgo + ", nom="
                + name + ", watt=" + watt + ", cpu=" + cpu + ", ram=" + ram + '}';
    }
}
