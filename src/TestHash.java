import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class TestHash {
  public static void main(String [] args) {
    int hashLength;
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
      hashLength = digest.getDigestLength();
      String s1= "1";
      String s2= "1";
      byte[] b1 = s1.getBytes("UTF-8");
      byte[] b2 = s2.getBytes("UTF-8");

      byte[] hash1 = digest.digest(b1);
      byte[] hash2 = digest.digest(b2);
      System.out.println("Hash1 has size " + hashLength + ": " + Arrays.toString(hash1));
      System.out.println("Hash2 has size " + hashLength + ": " + Arrays.toString(hash2));

    } catch(NoSuchAlgorithmException e) {
      System.out.println("No such algorithm");
      System.exit(1);
    } catch(UnsupportedEncodingException e) {
      System.out.println("No such encoding type");
      System.exit(1);
    }

  }
}
