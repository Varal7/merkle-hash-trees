import java.util.LinkedList;
import java.util.Arrays;


public class TestLogServer {
  public static void main(String [] args) {
    long tic,tac;
    // LogServer l1 = new LogServer("data/1.txt");
    // l1.append("1");
    // l1.append("1");
    // l1.append("1");
    // l1.append("1");
    // l1.tree.display();
    //
    // LinkedList<String> list = new LinkedList<String>();
    // list.add("1");
    // list.add("1");
    // list.add("1");
    // list.add("1");
    // LogServer l2 = new LogServer("data/1.txt");
    // l2.append(list);
    // l2.tree.display();
    // if (l1.equals(l2))
    //     System.out.println("l1 and l2 are equal");
    // else
    //     System.out.println("l1 and l2 are not equal");

    //
    // tic = System.currentTimeMillis();
    // LogServer l3 = new LogServer("data/commerce.log");
    // tac = System.currentTimeMillis();
    // System.out.println("l3 took " + String.valueOf(tac - tic) + "ms to load");



    //
    // LogServer l3 = new LogServer("data/8.txt");
    // l3.tree.display();
    // System.out.println("Audit path for 3");
    // for (Hash hash : l3.genPath(1)) {
    //   System.out.println(hash);
    // }
    // System.out.println("Proof for [0..5]");
    // for (Hash hash : l3.genPath(5)) {
    //   System.out.println(hash);
    // }


     LogServer l = new LogServer("data/8.txt");
     l.tree.display();
     System.out.println("Proof for 6 (= index 5):");
     for (Hash hash : l.genProof(5)) {
       System.out.println(hash);
     }
  }
}
