package fr.garnier.hibernatest;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

//        Car car = new Car();
//        car.setBrand("Altuntas-mobile");
//        car.setColor(Color.BLUE);
//        car.setWheels(4);
//
//        session.save(car);
//        session.flush();

        Criteria criteria = session.createCriteria(Car.class);
        for (Object o : criteria.list()) {
            System.out.println((Car) o);
        }

        session.close();
    }
}
