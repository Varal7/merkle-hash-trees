import java.util.LinkedList;


public class TestLogServer {
  public static void main(String [] args) {
      LogServer l = new LogServer("test.txt");
      System.out.println("Current root hash [1..6] : " + l.currentRootHash());
      l.append("Truc");
      l.append("Bidule");
      System.out.println("Current root hash [1..8] : " + l.currentRootHash());
      l.tree.display();
      LinkedList<byte[]> li = l.genPath(3);
      for (byte[] b : li) {
        System.out.println(b);
      }


  }
}
