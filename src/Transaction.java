import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Transaction {

	private String fromAddress;
	private String toAddress;
	private String signature;
	private int amount;
	
	public Transaction(String fromAddress, String toAddress, int amount) {
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.amount = amount;
		this.signature = null;
	}
	
	/*
	 * Calculates the hash of the transaction using the SHA-256 hash function
	 * @return the message digest of the transaction
	 */
	private String calculateHash() {

		String dataToHash = "" + this.fromAddress + this.toAddress + this.amount;
		
		MessageDigest digest;
		String encoded = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encoded;
	}
	
	private void signTransaction(String key) {
		
		// TODO: sign the transaction using the hash of the tx
		
	}
	
	private Boolean isValid() {
		
		//TODO: check if the tx has a valid signature
		
		return false;
	}
	
	/*
	 * Creates a readable version of the transaction
	 * @return String
	 */
	public String toString() {
		return "From: " + fromAddress + " To: " + toAddress + " Amount: " + amount;
	}
	
	public String getFromAddress() {
		return this.fromAddress;
	}
	
	public String getToAddress() {
		return this.toAddress;
	}
	
	public int getAmount() {
		return this.amount;
	}
}
