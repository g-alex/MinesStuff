/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author agarnier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Object object = new Object();
        Object[] objects = {object, object};
        List<Object> objectsList = new ArrayList<Object>();
        Main.coucou(object);
        Main.coucou(object, object);
        Main.coucou(objects);
        Main.coucou(objectsList.toArray());
        System.out.println(Enum.BOB.toString());
        Object objInt = new Integer(1);
        System.out.println(objInt.getClass());
        Integer loop = 0;
        Main.hej();
        Main.hej(Main.class.getSimpleName());
        Main.coucou();
        while (true) {
            A:
            {
                System.out.println("Yay, here is label A");
                if (++loop < 4) {
                    break A;
                } // if
                System.out.println("3 goto are enough !");
                break;
            } // A
        } // while
    } // static void main(String[])

    private static void hej(String... strings) {
        for (String string : strings) {
            System.out.println(string);
        }
    }

    private static void coucou(Object object) {
        System.out.println("Coucou toi !");
        test(object);
    }

    private static void coucou(Object... objects) {
        System.out.println("Coucou vous !!");
        test(objects);
    }

    private static void test(Object object) {
        System.out.println("Test !");
    }

    private static void test(Object... objects) {
        System.out.println("Tests !!");
    }
} // clas Main

