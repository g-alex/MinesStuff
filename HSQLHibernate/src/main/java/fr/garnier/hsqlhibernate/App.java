package fr.garnier.hsqlhibernate;

import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        System.out.println( "Hello World!" );

        Person person = new Person();
        person.setNom("Dumont");
        person.setPrenom("Frederic");

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.save(person);

        for (Object entry : session.createQuery("from Person").list()) {
            Person pEntry = (Person) entry;
            System.out.println(pEntry.getId() + ": " + pEntry.getNom() + " "
                    + pEntry.getPrenom());
        }

        session.flush();
        session.close();
    }
}
