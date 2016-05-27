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

  public MerkleTree(String s, int index){
    try{
      digest = MessageDigest.getInstance("SHA−256");
    }
    catch(NoSuchAlgorithmException e){
      System.out.println("No such algorithm");
    }

    hashLength = digest.getDigestLength();
    left = null;
    right = null;
    start = index;
    end = index;

    byte leaf[] = new byte[hashLength+1];
    leaf[0] = 0x01;

    try{
      System.arraycopy(s.getBytes("UTF-8"), 0, leaf, 1, hashLength);
    }
    catch(UnsupportedEncodingException e){
      System.out.println("No such encoding type");
    }

    hash = digest.digest(leaf);
  }

  public MerkleTree(MerkleTree l, MerkleTree r){
    if (l.end != r.start - 1){
      System.out.println("Trees not contiguous");
    }
    else{
      try{
        digest = MessageDigest.getInstance("SHA−256");
        hashLength = digest.getDigestLength();
      }
      catch(NoSuchAlgorithmException e){
        System.out.println("No such algorithm");
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
