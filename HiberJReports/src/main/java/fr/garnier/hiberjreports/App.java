package fr.garnier.hiberjreports;

import fr.garnier.hiberjreports.hibernate.HibernateUtil;
import fr.garnier.hiberjreports.hibernate.Mesure;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        System.out.println( "Hello World!" );

        Session session = HibernateUtil.getSessionFactory().openSession();

        for (Object entry : session.createQuery("from Mesure").list()) {
            System.out.println((Mesure) entry);
        } // for
    } // static void maint(String[])
} // class App

