import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class test {

	public static void main(String[] args) {
		
		Blockchain coin = new Blockchain(2, 100);
		Transaction tx = new Transaction("John", "Bill", 12);
		coin.addTransaction(tx);
		coin.minePendingTransactions("John");
		coin.addTransaction(tx);
		coin.minePendingTransactions("John");
		
		//coin.getLastBlock().updateBlockTransaction(new Transaction("Bill", "John", 1000));
		
		System.out.println(coin.toString());
		System.out.println("The balance of John: " + coin.getBalanceOfAddress("John"));
		System.out.println("The block chain is " + coin.isBlockChainValid());
		
	}
}
