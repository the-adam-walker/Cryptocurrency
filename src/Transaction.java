import java.nio.charset.StandardCharsets;
import java.security.*;

public class Transaction {

	private Wallet fromAddress;
	private Wallet toAddress;
	private byte[] signature;
	private int amount;

	public Transaction(Wallet fromAddress, Wallet toAddress, int amount) {
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
			encoded = convertToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return encoded;
	}

	/*
	 * takes a transaction object and signs the transcation filling the signature field
	 * @return byte[] signature
	 */
	public void signTransaction(Wallet w) {

		try {
			if (w.publicKey != this.fromAddress.publicKey) { //If the from address of the transaction is the same as the signer
				throw new Error("You cannot sign this transaction");
			}
			Signature signature = Signature.getInstance("SHA256withECDSA");
			signature.initSign(w.privateKey);
			byte[] data = calculateHash().getBytes();
			signature.update(data);
			this.signature = signature.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}


	}

	/*
	 * Checks to see if the current transcation is valid.
	 * @return Boolean T/F
	 */
	public Boolean isValid() {

		if (this.fromAddress == null) { // Is the fromAddress null i.e. mining reward
			return true;
		}

		try {
			if (this.signature == null || this.signature.length == 0) { //If there is no signature
				throw new Error("No signature in this transaction");
			}

			Signature signature = Signature.getInstance("SHA256withECDSA");
			signature.initVerify(this.fromAddress.publicKey);
			signature.update(calculateHash().getBytes());
			return signature.verify(this.signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}

		return false;

	}

	/*
	 * Creates a readable version of the transaction
	 * @return String
	 */
	public String toString() {
		if (fromAddress == null) {
			return "From: mining reward " + " To: " + convertToHex(toAddress.publicKey.getEncoded()) + " Amount: " + amount;
		}
		return "From: " + convertToHex(fromAddress.publicKey.getEncoded()) + " To: " + convertToHex(toAddress.publicKey.getEncoded()) + " Amount: " + amount;
	}

	/*
	 * Converts an array of data to hex and returns it as a string
	 */
	public String convertToHex(byte[] hash) {
		StringBuilder sb = new StringBuilder();
		for(byte b: hash) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	public Wallet getFromAddress() {
		return this.fromAddress;
	}

	public Wallet getToAddress() {
		return this.toAddress;
	}

	public int getAmount() {
		return this.amount;
	}
}
