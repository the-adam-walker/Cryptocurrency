 // Use java.security to generate public private key pairs
import java.security.*;

public class Wallet {
  public PublicKey publicKey;
  public PrivateKey privateKey;

  public Wallet() {
    KeyPair keyPair =  generatePrivatePublicKeyPair();
    this.publicKey = keyPair.getPublic();
    this.privateKey = keyPair.getPrivate();
  }

  /*
   * Generates a private public key pair using the EC algorithm of length 256 bits
   * @return KeyPair
   */
  private KeyPair generatePrivatePublicKeyPair() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
      keyPairGenerator.initialize(256);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      return keyPair;
    } catch (NoSuchAlgorithmException e ) {
      e.printStackTrace();
    }
    return null;
  }
  
  /*
	 * Converts an array of data to hex and returns it as a string
	 */
  public String convertKeyToHex(byte[] key) {
    StringBuilder sb = new StringBuilder();
    for(byte b: key) {
      sb.append(String.format("%02X", b));
    }
    return sb.toString();
  }

}
