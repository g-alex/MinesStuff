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
        Object o = new Object();
        Object[] objects = {o, o};
        List<Object> oList = new ArrayList<Object>();
        Main.coucou(o);
        Main.coucou(o, o);
        Main.coucou(objects);
        Main.coucou(oList.toArray());
        System.out.println(Enum.BOB.toString());
        Object object = new Integer(1);
        System.out.println(object.getClass());
        Integer loop = 0;
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

