/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.hsqldbtest;

import java.util.Date;

/**
 *
 * @author bobi
 */
public class Survey {

    private int id;
    private String name;
    private Date purchaseDate;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
