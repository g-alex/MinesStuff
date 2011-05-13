package fr.garnier.hibernatest;

import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Car car = new Car();
        car.setBrand("Mercedes");
        car.setColor(Color.GREEN);
        car.setWheels(4);

        session.save(car);
        session.flush();

        session.close();
    }
}
