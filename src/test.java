public class test {

	public static void main(String[] args) {

		Wallet John = new Wallet();
		Wallet Bill = new Wallet();
		Blockchain coin = new Blockchain(3, 100);
		Transaction tx = new Transaction(John, Bill, 10);
		tx.signTransaction(John);
		coin.addTransaction(tx);
		coin.minePendingTransactions(John);
		coin.addTransaction(tx);
		coin.minePendingTransactions(John);

		//coin.getLastBlock().updateBlockTransaction(new Transaction(Bill, John, 1000));

		System.out.println(coin.toString());
		System.out.println("The balance of John: " + coin.getBalanceOfWallet(John));
		System.out.println("The block chain is " + coin.isBlockChainValid());

	}
}
