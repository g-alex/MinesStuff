package btr.fr.garnier.btrpersist;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Dummy dummy = new Dummy();
        Persist.save(dummy);
        for (Object o : Persist.get(Dummy.class)) {
            System.out.println((Dummy) o);
            System.out.println(o.equals(dummy));
        } // for
        Persist.delete(dummy);
        System.out.println(Persist.get(Dummy.class).size());
    }
}
