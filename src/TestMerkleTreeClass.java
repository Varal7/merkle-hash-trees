public class TestMerkleTreeClass {
    public static void main(String [] args)
    {
        //Testing adding a String
        String s1 = new String("1");
        String s2 = new String("1");

        MerkleTree t1 = new MerkleTree(s1,1);
        MerkleTree t2 = new MerkleTree(s2,2);

        t1.display();
        t2.display();
        

    }
}
