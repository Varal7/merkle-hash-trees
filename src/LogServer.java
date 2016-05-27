import java.util.Scanner;
import java.security.MessageDigest;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.io.FileNotFoundException;



public class LogServer {
  MerkleTree tree;
  MessageDigest digest;

  public LogServer(String inputFile) {
    try {
      digest = MessageDigest.getInstance("MD5");
    }
    catch(NoSuchAlgorithmException e) {
      System.out.println("No such algorithm");
    }

    Queue<MerkleTree> merkleQueue = new LinkedList<MerkleTree>();
    Scanner input;
    try {
      input = new Scanner(new FileReader(inputFile));
      int i = 0;
      while(input.hasNextLine()) {
        String line = input.nextLine();
        MerkleTree merkle = new MerkleTree(line, i);
        try {
          merkleQueue.add(merkle);
        } catch(Exception e) {
          System.out.println("No more space available.");
        }
        i++;
      }
    }
    catch(FileNotFoundException e) {
      System.out.println("No such file");
      System.exit(1);
    }

    buildTree(merkleQueue);
  }

  public void buildTree(Queue<MerkleTree> merkleQueueInit) {
    Queue<MerkleTree>[] merkleQueues = new LinkedList[2];
    merkleQueues[0] = merkleQueueInit;
    merkleQueues[1] = new LinkedList<MerkleTree>();

    if(merkleQueues[0].size() == 0) System.out.println("Error : empty queue.");
    else {
      int a = 0;
      int b = 1;
      while(merkleQueues[a].size() != 1) {
        MerkleTree left, right;
        while(merkleQueues[a].size() > 0) {
          left = merkleQueues[a].poll();
          right = merkleQueues[a].poll();
          if(right == null) merkleQueues[b].add(left);
          else merkleQueues[b].add(new MerkleTree(left, right));
        }
        a = (a+1) % 2;
        b = (b+1) % 2;
      }
      tree = merkleQueues[a].poll();
    }
  }

  public byte[] currentRootHash() {
    return tree.hash;
  }

  public void append(String log) {
    tree = appendAux(log, tree);
  }

  public MerkleTree appendAux(String log, MerkleTree current) {
    if(current.size == current.nextPower) {
      MerkleTree newElement = new MerkleTree(log, current.size);
      return new MerkleTree(current, newElement);
    } else {
      return new MerkleTree(current.left, appendAux(log, current.right));
    }
  }

  public List<byte[]> genPath(int index) {
    MerkleTree current = tree;
    List<byte[]> listHash = new LinkedList();
    return makePath(index, current, listHash);
  }

  List<byte[]> makePath(int index, MerkleTree current, List<byte[]> listHash) {
    if(current.start == current.end && current.end == index){
      return listHash;
    } else if(current.left != null && current.left.end >= index) {
      listHash.add(current.right.hash);
      return makePath(index, current.left, listHash);
    } else if(current.right != null && current.right.end >= index) {
      listHash.add(current.left.hash);
      return makePath(index, current.right, listHash);
    } else {
      System.out.println("Index is out of range.");
      return listHash;
    }
  }
}
