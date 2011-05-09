package fr.garnier.hsqldbtest;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        HibernateUtil hibernateUtil = new HibernateUtil();
//        hibernateUtil.executeSQLCommand("create table survey "
//                + "(id int,name varchar, purchasedate date);");

        Session session = hibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        Survey survey = new Survey();
        survey.setName("Survey");
        survey.setPurchaseDate(new Date());
        System.out.println(survey.getId());

        session.save(survey);
        session.flush();
        tx.commit();

        for (Object cd : session.createQuery("from Survey").list()) {
            Survey elt = (Survey) cd;
            System.out.println(elt.getId() + ": " + elt.getName());
        } // for

        session.close();
        hibernateUtil.checkData("select * from survey");
    } // static void main(String[])
} // class App

