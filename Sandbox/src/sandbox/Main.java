/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox;

/**
 *
 * @author agarnier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Enum.BOB.toString());
        Object o = new Integer(1);
        System.out.println(o.getClass());
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
} // clas Main

