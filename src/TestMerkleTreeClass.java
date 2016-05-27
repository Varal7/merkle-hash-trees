public class TestMerkleTreeClass {
    public static void main(String [] args)
    {
        //Testing adding a String
        String s1 = new String("Hello");
        String s2 = new String("World");

        MerkleTree t1 = new MerkleTree(s1,1);
        MerkleTree t2 = new MerkleTree(s2,2);

        MerkleTree t = new MerkleTree(t1,t2);

    }
}
