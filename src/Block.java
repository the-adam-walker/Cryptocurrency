import java.util.ArrayList;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block {

	private int nonce;
	private Date timeStamp;
	private String hash;
	private String previousHash;
	private List<Transaction> txs;

	public Block(Date timeStamp, ArrayList<Transaction> txs){
		nonce = 0;
		this.timeStamp = timeStamp;
		this.txs = txs;
		this.previousHash = "";
		this.hash = computeHash();
	}

	public Block(Date timeStamp, List<Transaction> txs, String previousHash){
		nonce = 0;
		this.timeStamp = timeStamp;
		this.txs = txs;
		this.previousHash = previousHash;
		this.hash = computeHash();
	}

	/*
	 * toString() function that returns a readable and printable version of the block to the console
	 * @return String
	 */
	public String toString() {

		return  "\n\tHash: " + this.hash
				+ "\n\tPrevious Hash: " + this.previousHash
				+ "\n\tNonce: " + this.nonce
				+ "\n\tTime: " + this.timeStamp
				+ "\n\tTransactions: " + this.txs.toString();
	}

	/*
	 * This function will calculate the hash of the block using the
	 * SHA-256 digest
	 * @return hash of block
	 */
	public String computeHash() {

		String dataToHash = "" + this.timeStamp + this.txs + this.previousHash + this.nonce;

		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = convertToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.hash = encoded;
		return encoded;
	}

	/*
	 * Mines the block. It takes the nonce, number computed only once, and computes the hash.
	 * If the hash does not have the proper subset of zeros, as determined by the difficulty,
	 * then the nonce in incremented and the hash computed again. Once the hash has the
	 * proper subset of zeros, then the hash of the block is updated witht the correct hash.
	 * @param difficulty
	 * @return this.hash
	 */
	public String mineBlock(int difficulty) {
		this.nonce = 0;
		this.hash = this.computeHash();

		String zeros = "";
		for (int i = 0; i < difficulty; i++) { // Create a string of zeros of length difficulty
			zeros += "0";
		}

		while(this.hash.substring(0, difficulty).compareTo(zeros) != 0) {
			this.nonce++;
			this.hash = computeHash();
		}

		return this.hash;
	}

	/*
	 * Iterates through all the transactions in the list of transactions and if
	 * the transaction is invalid, return false, otherwise return true.
	 * @return Boolean T/F
	 */
	public Boolean hasValidTransactions() {

		for (int transaction = 0; transaction < txs.size(); transaction++) {
			if(!txs.get(transaction).isValid()) {
				return false;
			}
		}

		return true;
	}

	public List<Transaction> getTransactions() {
		return this.txs;
	}

	public String getPreviousHash() {
		return this.previousHash;
	}

	public String getHash() {
		return this.hash;
	}

	/*
	 * Updates the block with a new transaction for testing purposes
	 */
	public void updateBlockTransaction(Transaction txn) {
		this.txs.add(txn);
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
}
