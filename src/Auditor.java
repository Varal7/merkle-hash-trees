import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class Auditor {
  int size, hashLength;
  byte[] rootHash;
  LogServer server;
  MessageDigest digest;

  public Auditor(LogServer server) {
    size = server.tree.size;
    rootHash = server.tree.hash;
    this.server = server;

    digest = MessageDigest.getInstance("MD5");
    hashLength = digest.getDigestLength();
  }

  public boolean isMember(String event, int index) {
    // int index;
    // Deduce index from String event;
    LinkedList<byte[]> path = server.genPath(index);

    byte[] hash = new byte[hashLength];
    byte[] merge = new byte[1 + 2 * hashLength];

    hash = digest.digest(event);
    merge[0] = 0x01;
    System.arraycopy(hash, 0, merge, 1, hashLength);

    for(byte[] sent_hash : path) {
      System.arraycopy(sent_hash, 0, merge, 1 + hashLength, hashLength);
      hash = digest.digest(merge);
      System.arraycopy(hash, 0, merge, 1, hashLength);
    }

    if(hash == rootHash) return true;
    else return false;
  }

  public void isConsistent(LogServer newLogServer) {
    if(newLogServer.tree.size != this.size || newLogServer.tree.hash != this.rootHash) {
      LinkedList<byte[]> proofPath = server.genProof(this.size);

      int depthDifference, depthInitNew;
      depthDifference = ((int) Math.floor(Math.log(newLogServer.tree.size) / Math.log(2))) - ((int) Math.floor(Math.log(this.size) / Math.log(2)));
      depthInitNew = proofPath.length() - 1;
      depthInit = depthInit(this.size);

      byte[] hashNew = new byte[hashLength];
      byte[] hash = new byte[hashLength];
      byte[] mergeNew = new byte[1 + 2 * hashLength];
      byte[] merge = new byte[1 + 2 * hashLength];

      hashNew = proofPath.poll();
      System.arraycopy(hash, 0, hashNew, 1, hashLength);
      mergeNew[0] = 0x01;
      System.arraycopy(hashNew, 0, mergeNew, 1, hashLength);
      merge[0] = 0x01;
      System.arraycopy(hash, 0, merge, 1, hashLength);

      int currentDepthNew = depthInitNew - depthDifference;
      int currentDepth = depthInit;

      for(byte[] sentHash : proofPath) {
        System.arraycopy(sentHash, 0, mergeNew, 1 + hashLength, hashLength);
        hashNew = digest.digest(mergeNew);
        System.arraycopy(hashNew, 0, mergeNew, 1, hashLength);

        if(currentDepthNew == currentDepth && currentDepth > 0) {
          System.arraycopy(sentHash, 0, merge, 1 + hashLength, hashLength);
          hash = digest.digest(merge);
          System.arraycopy(hash, 0, merge, 1, hashLength);
          currentDepth--;
        }

        currentDepthNew--;
      }

      if(hashNew == newLogServer.tree.hash && hash == this.rootHash) return true;
      else return false;
    }
  }

  public int depthInit(int index) {
    int init = greatestPowerTwoSmaller(index);
    int k = 0;
    for(int i = init + 1; i <= index; ++i) k += (1 - powerPrimeFactorTwo(i));
    return k;
  }

  public int greatestPowerTwoSmaller(int index) {
    int p = 1;
    while(2 * p <= index) p *= 2;
    return p;
  }

  public int powerPrimeFactorTwo(int index) {
    int p = 0;
    int k = index;
    while(k % 2 == 0) {
      p++;
      k /= 2;
    }
    return p;
  }
}
