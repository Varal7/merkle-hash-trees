import java.util.Scanner;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;

public class LogServer{
  MerkleTree tree;

  public LogServer(String inputFile){
    Scanner input = new Scanner(new FileReader(inputFile));
    Queue<MerkleTree> MerkleQueue = new LinkedList<MerkleTree>();
    MessageDigest digest = MessageDigest.getInstance("SHA−256");

    int i = 0;
    while(input.hasNextLine()){
      String line = input.nextLine();
      MerkleTree Merkle = MerkleTree(line, i);

      try{
        MerkleQueue.add(Merkle);
      }
      catch(Exception e){
        System.out.println("No more space available.");
      }

      i++;
    }

    buildTree(MerkleQueue);
  }

  public void buildTree(Queue<MerkleTree> MerkleQueueInit){
    Queue<MerkleTree> MerkleQueues = new LinkedList<MerkleTree>[2];
    MerkleQueues[0] = MerkleQueueInit;
    MerkleQueues[1] = new LinkedList<MerkleTree>();

    if(MerkleQueues[0].size() == 0) System.out.println("Error : empty queue.");
    else{
      int a = 0;
      int b = 1;
      while(MerkleQueues[a].size() != 1){
        MerkleTree left, right;

        while(MerkleQueues[a].size() > 0){
          left = MerkleQueues[a].poll();
          right = MerkleQueues[a].poll();
          if(right == null) MerkleQueues[b].add(left);
          else MerkleQueues[b].add(new MerkleTree(left, right));
        }

        a = (a+1) % 2;
        b = (b+1) % 2;
      }

      tree = MerkleQueues[m].poll();
    }
  }

  public byte[] currentRootHash(){
    return tree.hash;
  }
}
