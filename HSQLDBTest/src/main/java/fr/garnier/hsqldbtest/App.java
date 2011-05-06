package fr.garnier.hsqldbtest;

import java.util.Date;
import org.hibernate.Session;

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

        Survey survey = new Survey();
        survey.setName("Survey");
        survey.setPurchaseDate(new Date());
        System.out.println(survey.getId());

        session.save(survey);
        session.flush();

        for (Object cd : session.createQuery("from Survey").list()) {
            System.out.println(((Survey) cd).getName());
        } // for

        session.close();
        hibernateUtil.checkData("select * from survey");
    } // static void main(String[])
} // class App

