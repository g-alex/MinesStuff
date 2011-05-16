/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.hibernatest;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author agarnier
 */
@Entity
@Table(name = "Car")
public class Car implements Serializable {

    public static final long serialID = 1L;
    @Id
    @GeneratedValue
    private Long id;
// permet de stocker l'enum comme un varchar (integer par défaut)
    @Enumerated(EnumType.STRING)
    private Color color;
    private Integer wheels;
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWheels() {
        return wheels;
    }

    public void setWheels(Integer wheels) {
        this.wheels = wheels;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", color=" + color + ", wheels=" + wheels + ", brand=" + brand + '}';
    }
}
