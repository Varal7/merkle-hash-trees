import java.util.LinkedList;


public class TestLogServer {
  public static void main(String [] args) {


    //  LogServer l1 = new LogServer("test.txt");
    //  l1.tree.display();


      //l2.tree.display();
      //if (l1.equals(l2)) System.out.println("l1 equals l2");


      LogServer l1 = new LogServer("data/1.txt");
      l1.append("1");
      l1.append("1");
      l1.append("1");
      l1.append("1");

      l1.tree.display();

      //
      // LinkedList<String> list = new LinkedList<String>();
      // list.add("1");
      // list.add("1");
      // list.add("1");
      // list.add("1");
      // LogServer l2 = new LogServer("data/1.txt");
      // l2.append(list);
      //
      // l2.tree.display();
      // if (l1.equals(l2))
      //     System.out.println("l1 and l2 are equal");
      // else
      //     System.out.println("l1 and l2 are not equal");

  }
}
