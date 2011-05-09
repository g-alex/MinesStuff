package fr.garnier.hsqltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hsqldb.jdbcDriver;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws SQLException {
        //        System.out.println( "Hello World!" );
        jdbcDriver jdbcDriver = new jdbcDriver();
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
        Statement statement = conn.createStatement();

        ResultSet resultat = statement.executeQuery("SELECT * FROM persons");
        while (resultat.next()) {
            System.out.println(resultat.getString("nom")+" "+resultat.getString("prenom"));
        }

        statement.executeQuery("SHUTDOWN");
        statement.close();
        conn.close();
    }
}
