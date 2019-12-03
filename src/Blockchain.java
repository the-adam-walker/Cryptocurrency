import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.security.*;

public class Blockchain {

	private List<Block> chain;
	private List<Transaction> pendingTxs;
	int difficulty;
	int miningReward;

	public Blockchain(int difficulty, int miningReward) {

		this.difficulty = difficulty;
		this.miningReward = miningReward;
		this.pendingTxs = new ArrayList<Transaction>();
		this.chain = new ArrayList<Block>();
		this.chain.add(generateGenesisBlock());

	}

	/*
	 * Creates the first (genesis) block of the block chain
	 *
	 * @return Block
	 */
	private Block generateGenesisBlock() {

		return new Block(new Date(), new ArrayList<Transaction>());

	}

	/*
	 * Creates a new block with the date as the date of creation, pending Transactions as the current list of
	 * transactions, and previous hash as the hash of the previous block in the block chain by using the
	 * getLastBlock() function call and calling the computeHash() function on the last block. The mining reward
	 * is also added to the list of pending transactions for future blocks to be mined
	 * @param minerAddress
	 */
	public void minePendingTransactions(Wallet minerAddress) {

		// Creates a block to be mined with the data
		Block block = new Block(new Date(), this.pendingTxs, getLastBlock().getHash());
		block.mineBlock(this.difficulty);

		// Adds the block to the blockchain
		this.chain.add(block);
		System.out.println("Block " + this.chain.size() + " Succesfully Mined!" + "\n\tHash: " + getLastBlock().getHash());

		// gives the reward to the miner for mining the block
		this.pendingTxs = new ArrayList<Transaction>(); // This resets the pending transactions array list to null;
		addTransaction(new Transaction(null, minerAddress, this.miningReward));
	}

	/*
	 * Adds a  transaction to the pending transcation list
	 * @param tx
	 */
	public void addTransaction(Transaction tx) {

		try {
			if(tx.getToAddress() == null && tx.getFromAddress() == null) {
				throw new Error("Transaction must include from and to address");
			}

			if(!tx.isValid()) {
				throw new Error("Cannot add invalid transaction to chain");
			}
		} catch (Error e) {
			e.printStackTrace();
		}

		this.pendingTxs.add(tx);

	}

	/*
	 * Computes the total balance associated with a wallet following some logic
	 * if the address is equal to the fromAddress field then the amount associated
	 * with the transaction is subtracted from the total balance. If the address is equal
	 * to the toAddress field then the amount associated with the transaction is added to the
	 * total balance
	 * @param address
	 * @return int balance
	 */
	public int getBalanceOfWallet(Wallet address) {

		int balance = 0;

		for (int block = 0; block < this.chain.size(); block++) { // Iterates through each block in the blockchain
				Block b = this.chain.get(block);
			for (int transaction = 0; transaction < b.getTransactions().size(); transaction++) { // iterates through each transaction in the transaction list
				if(b.getTransactions().get(transaction).getFromAddress() != null) {
					if(address.publicKey == (b.getTransactions().get(transaction).getFromAddress().publicKey)) {
						balance -= b.getTransactions().get(transaction).getAmount();
					}
				}
				if(address.publicKey == (b.getTransactions().get(transaction).getToAddress().publicKey)) {
					balance += b.getTransactions().get(transaction).getAmount();
				}
			}
		}
		return balance;
	}

	/*
	 *  Iterates through each block and compares the previousHash field of the
	 *  current block with the hash of the previous block. It alco compares the hash of
	 *  the current block with the a computed hash of the current block to check that
	 *  the chain has not been modified
	 * @return String
	 */
	public String isBlockChainValid() {

		for (int block = this.chain.size() - 1; block > 0; block--) {
			if (!this.chain.get(block).hasValidTransactions()) {
				return "Invalid";
			}
			String previousBlockHash = this.chain.get(block - 1).getHash();
			String currentBlockPreviousHash = this.chain.get(block).getPreviousHash();
			if (previousBlockHash.compareTo(currentBlockPreviousHash) != 0) {
				return "Invalid";
			}
			String currentBlockHash = this.chain.get(block).getHash();
			String computedCurrentBlockHash = this.chain.get(block).mineBlock(this.difficulty);
			if(currentBlockHash.compareTo(computedCurrentBlockHash) != 0) {
				return "Invalid";
			}
		}
		return "Valid";
	}

	/*
	 * Grabs the last block in the blockchain
	 * @return Block
	 */
	public Block getLastBlock() {
		return this.chain.get(this.chain.size() - 1);
	}

	/*
	 * Creates a String version of the block chain to output to console
	 * upon request
	 * @return String message
	 */
	public String toString() {

		String message = "";
		for (int block = 0; block < chain.size(); block++) {
			message += "Block " + (block + 1) + ": " + this.chain.get(block).toString() + "\n";
		}

		return message;
	}

}
