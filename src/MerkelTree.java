import java.security.MessageDigest;

class MerkelTree{
  byte[] hash;
  MerkelTree left;
  MerkelTree right;
  int start;
  int end;

  MessageDigest digest = MessageDigest.getInstance("SHA−256");
  int hashLength = digest.getDigestLength();

  MerkelTree(String s, int index){
    left = null;
    right = null;
    start = index;
    end = index;

    byte[] leaf = ("\u00" + s).getBytes("UTF−8");
    hash = digest.digest(leaf);
  }

  MerkelTree(MerkelTree l, MerkelTree r){
    left = l;
    right = r;
    start = l.start;
    end = r.end;

    byte[] merge = new byte[1 + 2 * hashLength];
    merge[0] = 0x01;
    arraycopy(l.hash, 0, merge, 1, hashLength);
    arraycopy(r.hash, 0, merge, 1 + hashLength, hashLength);

    hash = digest.digest(merge);
  }
}
