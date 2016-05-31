import java.util.Scanner;
import java.io.FileReader;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;

public class LogServer {
  MerkleTree tree;

  public LogServer(MerkleTree t) {
    tree = t;
  }

  public LogServer(String inputFile) {

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

  public Hash currentRootHash() {
    return tree.hash;
  }

  public void append(String log) {
    tree = appendSingle(log, tree, tree.size);
  }

  public void append(LinkedList<String> list) {
    tree = appendSeveral(list, tree, tree.size);
  }

  public MerkleTree appendSingle(String log, MerkleTree current, int rank) {
    if(current == null) return new MerkleTree(log, 0);
    else if(current.size == current.nextPower) {
      MerkleTree newElement = new MerkleTree(log, rank);
      return new MerkleTree(current, newElement);
    } else {
      return new MerkleTree(current.left, appendSingle(log, current.right, rank));
    }
  }

  public MerkleTree appendSeveral(LinkedList<String> list, MerkleTree current, int rank) {
    if(list.isEmpty()) return current;
    else if(current == null){
      current = new MerkleTree(list.poll(), 0);
      return appendSeveral(list, current, rank+1);
    }
    else{
      int nbElements = current.nextPower - current.size;
      MerkleTree result;

      if(nbElements == 0){
        result = new MerkleTree(current, new MerkleTree(list.poll(), rank));
        return appendSeveral(list, result, rank + 1);
      }
      else if(list.size() > nbElements){
        LinkedList<String> elements = list;
        LinkedList<String> completeTreeElements = new LinkedList<String>();

        for(int i = 0; i < nbElements; i++) completeTreeElements.add(elements.poll());

        result = new MerkleTree(current.left, appendSeveral(completeTreeElements, current.right, rank));
        result = new MerkleTree(result, new MerkleTree(elements.poll(), rank + nbElements));
        return new MerkleTree(result.left, appendSeveral(elements, result.right, rank + nbElements + 1));
      }
      else{
        if(list.size() == 1) return appendSingle(list.poll(), current, rank);
        else return new MerkleTree(current.left, appendSeveral(list, current.right, rank));
      }
    }
  }

  public LinkedList<Hash> genPath(int index) {
    MerkleTree current = tree;
    LinkedList<Hash> listHash = new LinkedList<Hash>();
    return makePath(index, current, listHash);
  }

  LinkedList<Hash> makePath(int index, MerkleTree current, LinkedList<Hash> listHash) {
    if(current.start == current.end && current.end == index){
      return listHash;
    } else if(current.left != null && current.left.end >= index) {
      listHash.addFirst(current.right.hash);
      return makePath(index, current.left, listHash);
    } else if(current.right != null && current.right.end >= index) {
      listHash.addFirst(current.left.hash);
      return makePath(index, current.right, listHash);
    } else {
      System.out.println("Index is out of range.");
      return listHash;
    }
  }

  public LinkedList<Hash> genProof(int index){
    MerkleTree current = tree;
    LinkedList<Hash> listHash = new LinkedList<Hash>();
    return makeProof(index, current, listHash);
  }

  LinkedList<Hash> makeProof(int index, MerkleTree current, LinkedList<Hash> listHash) {
    if(current.end < index || current.start > index) {
      System.out.println("Index is out of range.");
      return listHash;
    } else {
        if(current.end == index){
          listHash.addFirst(current.hash);
          return listHash;
        } else if(current.left != null && current.left.end < index) {
          listHash.addFirst(current.left.hash);
          return makeProof(index, current.right, listHash);
        } else if(current.left != null && current.left.end >= index) {
          listHash.addFirst(current.right.hash);
          listHash = makeProof(index, current.left, listHash);
          return listHash;
        } else {
          System.out.println("You forgot to put some text here"); //TODO
          return null;
        }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!LogServer.class.isAssignableFrom(obj.getClass()))
      return false;
    final LogServer other = (LogServer) obj;
    return tree.equals(other.tree);
  }
}
