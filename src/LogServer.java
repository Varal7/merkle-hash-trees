import java.util.Scanner;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;

public class LogServer{
  MerkleTree tree;
  MessageDigest digest;
  int size;

  public LogServer(String inputFile){
    Scanner input = new Scanner(new FileReader(inputFile));
    Queue<MerkleTree> MerkleQueue = new LinkedList<MerkleTree>();
    digest = MessageDigest.getInstance("SHA−256");

    int i = 0;
    while(input.hasNextLine()){
      String line = input.nextLine();
      MerkleTree Merkle = MerkleTree(line, i);

      try{
        MerkleQueue.add(Merkle);
        size++;
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

  public void append(String log){
    MerkleTree current = tree;
    tree.hash = auxAppend(log, current);
  }

  public List<byte[]> genPath(int index){
    MerkleTree current = tree;
    List<byte[]> listHash = new List<byte[]>();
    makePath(index, current, listHash);

    return listHash;
  }

  void makePath(int index, MerkleTree current, List<byte[]> listHash){
    if(current.start == current.end && current.end == index){

    }
    else if(current.left != null && current.left.end >= index){
      listHash.add(current.right.hash);
      makePath(index, current.left, listHash);
    }
    else if(current.right != null && current.right.end >= index){
      listHash.add(current.left.hash);
      makePath(index, current.right, listHash);
    }
    else{
      System.out.println("Index is out of range.");
    }
  }
}
