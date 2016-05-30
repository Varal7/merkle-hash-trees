import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MerkleTree{
  static final boolean DEV_MODE = true;

  byte[] hash;
  MerkleTree left, right;
  int start, end, hashLength, nextPower, size;
  MessageDigest digest;

  public MerkleTree(MerkleTree old) {
    for (int i = 0; i < old.hash.length; i++) hash[i] = old.hash[i];
    left = new MerkleTree(old.left);
    right = new MerkleTree(old.right);
    start = old.start;
    end = old.end;
    nextPower = old.nextPower;
    size = old.size;
    try {
      digest = MessageDigest.getInstance("MD5");
      hashLength = digest.getDigestLength();
    } catch(NoSuchAlgorithmException e) {
      System.out.println("No such algorithm");
      System.exit(1);
    }
  }

  public MerkleTree(String s, int index) {
    try {
      digest = MessageDigest.getInstance("MD5");
      hashLength = digest.getDigestLength();
    } catch(NoSuchAlgorithmException e) {
      System.out.println("No such algorithm");
      System.exit(1);
    }

    left = null;
    right = null;
    start = index;
    end = index;
    size = 1;
    nextPower = 1;

    try {
      byte[] b = s.getBytes("UTF-8");
      int bLength = b.length;
      byte leaf[] = new byte[bLength+1];
      System.arraycopy(b, 0, leaf, 1, bLength);
      leaf[0] = 0x00;
      hash = digest.digest(leaf);
    } catch(UnsupportedEncodingException e) {
      System.out.println("No such encoding type");
      System.exit(1);
    }
  }

  public MerkleTree(MerkleTree l, MerkleTree r) {
    if (l.end != r.start - 1) {
      System.out.println("Trees not contiguous, left end at " + l.end + "; right starts at " + r.start );
      System.exit(1);
    } else {
      try {
        digest = MessageDigest.getInstance("MD5");
        hashLength = digest.getDigestLength();
      } catch(NoSuchAlgorithmException e) {
        System.out.println("No such algorithm");
        System.exit(1);
      }
      left = l;
      right = r;
      start = l.start;
      end = r.end;
      size = l.size + r.size;
      if(size < Math.max(l.nextPower, r.nextPower)) nextPower = Math.max(l.nextPower, r.nextPower);
      else nextPower = 2 * Math.max(l.nextPower, r.nextPower);

      byte[] merge = new byte[1 + 2 * hashLength];
      merge[0] = 0x01;
      System.arraycopy(l.hash, 0, merge, 1, hashLength);
      System.arraycopy(r.hash, 0, merge, 1 + hashLength, hashLength);
      hash = digest.digest(merge);
    }
  }

  public void display() {
    System.out.println("Size of tree: " + size);
    display_offset(0);
  }

  void display_offset(int offset) {
    for (int i = 0; i < offset; i ++) {
      System.out.print("  ");
    }
    System.out.println("["+ start + "," + end + "]: " + Arrays.toString(hash));
    if (left != null) left.display_offset(offset+1);
    if (right != null) right.display_offset(offset+1);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!MerkleTree.class.isAssignableFrom(obj.getClass()))
      return false;
    final MerkleTree other = (MerkleTree) obj;

    if (start != other.start || end != other.end || nextPower != other.nextPower || size != other.size) {
      if (DEV_MODE) System.out.println("Merkle trees differ at node: "+ "["+ start + "," + end + "] :");
      return false;
    }
    for (int i = 0; i < hash.length; i++) {
      if (hash[i] != other.hash[i]) {
        if (DEV_MODE) System.out.println("Merkle trees differ by hash at node: "+ "["+ start + "," + end + "]");
        return false;
      }
    }
    if (left == null || right == null) {
      assert(right == null && left ==null);
      if (other.left == null || other.right == null) {
        assert(other.right == null && other.left == null);
        return true;
      }
      if (DEV_MODE) System.out.println("Merkle trees differ at node: "+ "["+ start + "," + end + "] :");
      return false;
    }
    return (left.equals(other.left) && (right.equals(other.right)));
  }

}
