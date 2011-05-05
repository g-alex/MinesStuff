/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier.hsqldbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author bobi
 */
public class HibernateUtil {

    Session session;
    Statement st;

    public HibernateUtil() throws Exception {
        SessionFactory sessionFactory =
                new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();

        // Load the JDBC driver.
        Class.forName("org.hsqldb.jdbcDriver");
        System.out.println("Driver Loaded.");
        // Establish the connection to the database.
        String url = "jdbc:hsqldb:data/tutorial";

        Connection conn = DriverManager.getConnection(url, "sa", "");
        System.out.println("Got Connection.");
        st = conn.createStatement();
    }

    public Session getSession() {
        return session;
    }

    public void executeSQLCommand(String sql) throws Exception {
        st.executeUpdate(sql);
    }

    public void checkData(String sql) throws Exception {
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metadata = rs.getMetaData();

        for (int i = 0; i < metadata.getColumnCount(); i++) {
            System.out.print("\t" + metadata.getColumnLabel(i + 1));
        }
        System.out.println("\n----------------------------------");

        while (rs.next()) {
            for (int i = 0; i < metadata.getColumnCount(); i++) {
                Object value = rs.getObject(i + 1);
                if (value == null) {
                    System.out.print("\t       ");
                } else {
                    System.out.print("\t" + value.toString().trim());
                }
            }
            System.out.println("");
        }
    }
}
