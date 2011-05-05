package fr.garnier.hsqldbtest;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        HibernateUtil hibernateUtil = new HibernateUtil();
        hibernateUtil.executeSQLCommand("create table survey "
                + "(id int,name varchar, purchasedate date);");

        Session session = hibernateUtil.getSession();

        Survey survey = new Survey();
        survey.setName("Survey");
        survey.setPurchaseDate(new Date());
        System.out.println(survey.getId());

        session.save(survey);
        session.flush();

        List cds = session.createQuery("from Survey").list();
        Iterator iter = cds.iterator();
        while (iter.hasNext()) {
            Survey cd = (Survey) iter.next();
            System.out.println(cd.getName());
        }

        session.close();
        hibernateUtil.checkData("select * from survey");
    }
}
