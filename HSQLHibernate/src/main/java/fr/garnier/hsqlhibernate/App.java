package fr.garnier.hsqlhibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        Person person = new Person();
        person.setNom("Dumont");
        person.setPrenom("Frédéric");

        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        Session session = sessFact.openSession();

        Transaction transact = session.beginTransaction();
        System.out.println((Long) session.save(person));
        transact.commit();

        for (Object entry : session.createQuery("from Person").list()) {
            Person pEntry = (Person) entry;
            System.out.println(pEntry.getId() + ": " + pEntry.getNom() + " "
                    + pEntry.getPrenom());
        }

        session.close();
        sessFact.close();

    }
}
