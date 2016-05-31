

public class TestAuditor {
  public static void main(String [] args) {

  //   LogServer old = new LogServer("data/6.txt");
  //   LogServer l = new LogServer("data/8.txt");
  //   Auditor b = new Auditor(old);
   //
  //   System.out.println("Old tree:");
  //   old.tree.display();
  //   System.out.println("New tree:");
  //   l.tree.display();
  //   // Auditor a = new Auditor(l);
  //   // System.out.println("Showing path");
  //   // for (Hash hash : l.genPath(2)) {
  //   //   System.out.println(hash);
  //   // }
  //   // System.out.println("Showing successive hashes");
  //   // if (a.isMember("3",2))
  //   //   System.out.println("3 is member at index 2");
  //   // else
  //   //   System.out.println("3 is not member at index 2");
  //   //
   //
    // System.out.println("Proof for 6 (= index 5):");
    // for (Hash hash : l.genProof(5)) {
    //   System.out.println(hash);
    // }
   //
  //  System.out.println("========================");
   //
   //
   //
  //   if (b.isConsistent(l))
  //     System.out.println("l extends old");
  //   else
  //     System.out.println("l does not extend old");

    // LogServer l = new LogServer("data/1.txt");
    // Auditor a = new Auditor(l);
    // LogServer l1 = new LogServer("data/1.txt");
    // LogServer l2 = new LogServer("data/2.txt");
    // LogServer l3 = new LogServer("data/3.txt");
    // LogServer l4 = new LogServer("data/4.txt");
    // LogServer l5 = new LogServer("data/5.txt");
    // LogServer l6 = new LogServer("data/6.txt");
    // LogServer l7 = new LogServer("data/7.txt");
    // LogServer l8 = new LogServer("data/8.txt");
    // if (a.isConsistent(l1))  System.out.println("l1 extends old");
    // else   System.out.println("l1 does not extend old");
    // if (a.isConsistent(l2))  System.out.println("l2 extends old");
    // else   System.out.println("l2 does not extend old");
    // if (a.isConsistent(l3))  System.out.println("l3 extends old");
    // else   System.out.println("l3 does not extend old");
    // if (a.isConsistent(l4))  System.out.println("l4 extends old");
    // else   System.out.println("l4 does not extend old");
    // if (a.isConsistent(l5))  System.out.println("l5 extends old");
    // else   System.out.println("l5 does not extend old");
    // if (a.isConsistent(l6))  System.out.println("l6 extends old");
    // else   System.out.println("l6 does not extend old");
    // if (a.isConsistent(l7))  System.out.println("l7 extends old");
    // else   System.out.println("l7 does not extend old");
    // if (a.isConsistent(l8))  System.out.println("l8 extends old");
    // else   System.out.println("l8 does not extend old");


    LogServer oldS = new LogServer("data/old.txt");
    LogServer newS = new LogServer("data/new.txt");
    Auditor a = new Auditor(oldS);

    if (a.isConsistent(newS))  System.out.println("new extends old");
     else   System.out.println("new does not extend old");
  }
}
