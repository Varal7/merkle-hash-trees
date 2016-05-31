import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
  byte[] hash;
  MessageDigest digest;
  int hashLength;

  public Hash() {
    try {
      digest = MessageDigest.getInstance("MD5");
      hashLength = digest.getDigestLength();
    } catch(NoSuchAlgorithmException e) {
      System.out.println("No such algorithm");
      System.exit(1);
    }
    hash = new byte[hashLength];
  }

  public Hash(String s) {
    this();
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

  public Hash(Hash old) {
    this();
    assert(hashLength == old.hashLength);
    for (int i = 0; i < old.hash.length; i++) hash[i] = old.hash[i];
  }

  public Hash(Hash h1, Hash h2) {
    this();
    assert(h1.hashLength == h2.hashLength);
    assert(hashLength == h1.hashLength);
    byte[] merge = new byte[1 + 2 * hashLength];
    merge[0] = 0x01;
    System.arraycopy(h1.hash, 0, merge, 1, hashLength);
    System.arraycopy(h2.hash, 0, merge, 1 + hashLength, hashLength);
    hash = digest.digest(merge);
  }

  @Override
  public String toString() {
    return Arrays.toString(hash);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (!Hash.class.isAssignableFrom(obj.getClass()))
      return false;
    final Hash other = (Hash) obj;

    for (int i = 0; i < hash.length; i++) {
      if (hash[i] != other.hash[i]) {
        return false;
      }
    }
    return true;
  }
}
