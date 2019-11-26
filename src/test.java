import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.security.*;
public class test {

	public static void main(String[] args) {

		Blockchain coin = new Blockchain(3, 100);
		Transaction tx = new Transaction("John", "Bill", 12);
		coin.addTransaction(tx);
		coin.minePendingTransactions("John");
		coin.addTransaction(tx);
		coin.minePendingTransactions("John");

		//coin.getLastBlock().updateBlockTransaction(new Transaction("Bill", "John", 1000));

		System.out.println(coin.toString());
		System.out.println("The balance of John: " + coin.getBalanceOfAddress("John"));
		System.out.println("The block chain is " + coin.isBlockChainValid());

		Keys keys = new Keys();
		System.out.println("Public: " + keys.convertKeyToHex(keys.publicKey.getEncoded()));
		System.out.println("Private: " + keys.convertKeyToHex(keys.privateKey.getEncoded()));
	}
}
