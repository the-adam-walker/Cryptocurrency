 // Use java.security to generate public private key pairs
import java.security.*;

public class Keys {
  public PublicKey publicKey;
  public PrivateKey privateKey;

  public Keys() {
    KeyPair keyPair =  generatePrivatePublicKeyPair();
    this.publicKey = keyPair.getPublic();
    this.privateKey = keyPair.getPrivate();
  }

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

  public String convertKeyToHex(byte[] key) {
    StringBuilder sb = new StringBuilder();
    for(byte b: key) {
      sb.append(String.format("%02X", b));
    }
    return sb.toString();
  }

}
