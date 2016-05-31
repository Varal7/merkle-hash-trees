public class MerkleTree{
  static final boolean DEV_MODE = false;

  Hash hash;
  MerkleTree left, right;
  int start, end, nextPower, size;
  //TODO :    f = v && !(v & (v - 1)); returns true iff v is a power of 2


  public MerkleTree(MerkleTree old) {
    hash = new Hash(old.hash);
    left = new MerkleTree(old.left);
    right = new MerkleTree(old.right);
    start = old.start;
    end = old.end;
    nextPower = old.nextPower;
    size = old.size;
  }

  public MerkleTree(String s, int index) {
    left = null;
    right = null;
    start = index;
    end = index;
    size = 1;
    nextPower = 1;
    hash = new Hash(s);
  }

  public MerkleTree(MerkleTree l, MerkleTree r) {
    if (l.end != r.start - 1) {
      System.out.println("Trees not contiguous, left end at " + l.end + "; right starts at " + r.start );
      System.exit(1);
    } else {
      left = l;
      right = r;
      start = l.start;
      end = r.end;
      size = l.size + r.size;
      if(size < Math.max(l.nextPower, r.nextPower)) nextPower = Math.max(l.nextPower, r.nextPower);
      else nextPower = 2 * Math.max(l.nextPower, r.nextPower);
      hash = new Hash(l.hash, r.hash);
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
    System.out.println("["+ start + "," + end + "]: " + hash.toString());
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
    if (!hash.equals(other.hash)) {
        if (DEV_MODE) System.out.println("Merkle trees differ by hash at node: "+ "["+ start + "," + end + "]");
        return false;
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
