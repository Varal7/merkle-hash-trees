

public class TestAuditor {
  public static void main(String [] args) {

    LogServer l = new LogServer("data/8.txt");
    l.tree.display();
    Auditor a = new Auditor(l);
    System.out.println("Showing path");
    for (Hash hash : l.genPath(2)) {
      System.out.println(hash);
    }
    System.out.println("Showing successive hashes");
    if (a.isMember("3",2))
      System.out.println("3 is member at index 2");
    else
      System.out.println("3 is not member at index 2");
  }
}
