import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class MerkleTree{
  byte[] hash;
  MerkleTree left;
  MerkleTree right;
  int start;
  int end;
  int hashLength;
  MessageDigest digest;

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
      System.out.println("Trees not contiguous");
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
      byte[] merge = new byte[1 + 2 * hashLength];
      merge[0] = 0x01;
      System.arraycopy(l.hash, 0, merge, 1, hashLength);
      System.arraycopy(r.hash, 0, merge, 1 + hashLength, hashLength);
      hash = digest.digest(merge);
    }
  }
}
