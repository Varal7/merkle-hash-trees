public class TestHash {
  public static void main(String [] args) {
      String s1= "1";
      String s2= "1";

      Hash h1 = new Hash(s1);
      Hash h2 = new Hash(s2);

      System.out.println("Hash1 has size " + h1.hashLength + ": " + h1.toString());
      System.out.println("Hash2 has size " + h2.hashLength + ": " + h2.toString());

  }
}
