package fr.garnier.hibernatest;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

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
        ProjectionList projectionList = Projections.projectionList().add(Projections.property("brand")).add(Projections.property("wheels"));
        criteria.setProjection(projectionList);
        displayObjectsList(criteria.list());

        session.close();
    }

    static public void displayObjectsList(List list) {
        Iterator iter = list.iterator();
        if (!iter.hasNext()) {
            System.out.println("No objects to display.");
            return;
        }
        while (iter.hasNext()) {
            System.out.println("New object");
            Object[] obj = (Object[]) iter.next();
            for (int i = 0; i < obj.length; i++) {
                System.out.println(obj[i]);
            }


        }
    }
}
