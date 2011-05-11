/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.hiberjreports.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bobi
 */
@Entity
@Table(name = "mesures")
public class Mesure {

    @Column(name = "category")
    private String category;
    @Column(name = "time")
    @Id
    private long time;
    @Column(name = "nom")
    @Id
    private String nom;
    @Column(name = "watt")
    private double watt;
    @Column(name = "cpu")
    private double cpu;
    @Column(name = "ram")
    private double ram;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
        return "Mesure{" + "category=" + category + "time=" + time + "nom=" + nom + "watt=" + watt + "cpu=" + cpu + "ram=" + ram + '}';
    }
}
