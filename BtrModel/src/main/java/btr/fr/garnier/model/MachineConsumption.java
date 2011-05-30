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
    private MachineType category;
    private long time;
    private String nom;
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
     * @param nom Machine name
     * @param watt Watt consumption value
     * @param cpu CPU consumption value
     * @param ram RAM consumption value
     */
    public MachineConsumption(MachineType category, long time, String nom, double watt, double cpu, double ram) {
        this.category = category;
        this.time = time;
        this.nom = nom;
        this.watt = watt;
        this.cpu = cpu;
        this.ram = ram;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MachineType getCategory() {
        return category;
    }

    public void setCategory(MachineType category) {
        this.category = category;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getWatt() {
        return watt;
    }

    public void setWatt(double watt) {
        this.watt = watt;
    }

    @Override
    public String toString() {
        return "Mesures{" + "category=" + category + ", time=" + time + ", nom=" + nom + ", watt=" + watt + ", cpu=" + cpu + ", ram=" + ram + '}';
    }
}
