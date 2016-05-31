import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class Auditor {
  static final boolean DEV_MODE = true;
  int size;
  Hash rootHash;
  LogServer server;

  public Auditor(LogServer server) {
    size = server.tree.size;
    rootHash = server.tree.hash;
    this.server = server;
  }

  public boolean isMember(String event, int index) {
    // int index;
    // Deduce index from String event;
    LinkedList<Hash> path = server.genPath(index);

    Hash hash = new Hash(event);
    if (DEV_MODE)  System.out.println(hash.toString());


    Hash merge;

    for(Hash sent_hash : path) {
      hash = new Hash(hash, sent_hash);
      if (DEV_MODE)  System.out.println(hash.toString());
    }

    if(hash.equals(rootHash)) return true;
    else return false;
  }

  public void isConsistent(LogServer newLogServer) {
    if(newLogServer.tree.size != this.size || !(newLogServer.tree.hash.equals(this.rootHash)) {
      LinkedList<Hash> proofPath = server.genProof(this.size);

      int depthDifference, depthInitNew;
      depthDifference = ((int) Math.floor(Math.log(newLogServer.tree.size) / Math.log(2))) - ((int) Math.floor(Math.log(this.size) / Math.log(2)));
      depthInitNew = proofPath.length() - 1;
      depthInit = depthInit(this.size);

      // byte[] hashNew = new byte[hashLength];
      // byte[] hash = new byte[hashLength];
      // byte[] mergeNew = new byte[1 + 2 * hashLength];
      // byte[] merge = new byte[1 + 2 * hashLength];

      Hash hashNew;
      Hash hash;
      Hash merge;
      Hash mergeNew;

      hashNew = proofPath.poll();
      System.arraycopy(hash, 0, hashNew, 1, hashLength);
      mergeNew[0] = 0x01;
      System.arraycopy(hashNew, 0, mergeNew, 1, hashLength);
      merge[0] = 0x01;
      System.arraycopy(hash, 0, merge, 1, hashLength);
      //TODO : WTF ?


      int currentDepthNew = depthInitNew - depthDifference;
      int currentDepth = depthInit;

      for(Hash sentHash : proofPath) {
        // System.arraycopy(sentHash, 0, mergeNew, 1 + hashLength, hashLength);
        // hashNew = digest.digest(mergeNew);
        // System.arraycopy(hashNew, 0, mergeNew, 1, hashLength);
        mergeNew = new Hash(mergeNew, sentHash);

        if(currentDepthNew == currentDepth && currentDepth > 0) {
          // System.arraycopy(sentHash, 0, merge, 1 + hashLength, hashLength);
          // hash = digest.digest(merge);
          // System.arraycopy(hash, 0, merge, 1, hashLength);
          merge = new Hash(merge, sentHash);
          currentDepth--;
        }

        currentDepthNew--;
      }

      if(hashNew.equals(newLogServer.tree.hash) && hash.equals(this.rootHash)) return true;
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
    //TODO : cas index = 0 ?
    // int p = 1;
    // while(2 * p <= index) p *= 2;
    // return p;
    return Integer.hightOneBit(index); // TODO : ou index - 1 ?
  }

  public int powerPrimeFactorTwo(int index) {
    // int p = 0;
    // int k = index;
    // while(k % 2 == 0) {
    //   p++;
    //   //k /= 2;
    //   k = k>>1;
    // }
    // return p;
    return Integer.numberOfTrailingZeros(index);
  }
}
