package quan.blockchain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.GsonBuilder;

public class Main {

	// refer https://viblo.asia/p/tao-1-blockchain-voi-java-part-4-1VgZvNkOZAw

	public static Map<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); // list of unspent
																									// transaction
	static BlockChain blockchain = new BlockChain();
	public static Wallet walletA;
	public static Wallet walletB;
	public static Wallet coinbase;
	public static float minimunTransaction = 0.1f;

	public static Transaction genericTransaction;

	public static void main(String[] args) {
		walletA = new Wallet();
		walletB = new Wallet();
		coinbase = new Wallet();

		genericTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		// signature for genericTransaction address
		genericTransaction.generateSignature(coinbase.privateKey);
		genericTransaction.setTransactionId("0"); // manual set transactionId
		// manual add the TransactionOutput
		genericTransaction.transactionOutput.add(new TransactionOutput(genericTransaction.reciepient,
				genericTransaction.value, genericTransaction.transactionId));
		// it is important to store our first transaction in the UTXOs list
		UTXOs.put(genericTransaction.transactionOutput.get(0).getId(), genericTransaction.transactionOutput.get(0));
		

		System.out.println("Creating the generic block");
		Block generic = new Block("Generis Block", "0");
		generic.addTransaction(genericTransaction);
		//manual add genericBlock
		blockchain.blockchain.add(generic);
		
		Block block1 = new Block("The first block", generic.getHash());
		System.out.println("Balance is of walletA: " + walletA.getBalance() + " Coin");
		System.out.println("\nWalletA is Attempting to send funds (10) to WalletB...");
		block1.addTransaction(walletA.sendFund(walletB.publicKey, 10));
		blockchain.blockchain.add(block1);
		
		System.out.println("Wallet A balance: " + walletA.getBalance());
		System.out.println("Wallet B balance " + walletB.getBalance());
		
		/*Block block2 = new Block("Block 2", block1.getHash());
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFund(walletB.publicKey, 1000f));*/
		
		/*Block block2 = new Block("Block 3", block1.getHash());
		System.out.println("WalletB sent 5 coin to WalletA: ");
		block2.addTransaction(walletB.sendFund(walletA.publicKey, 0));
		blockchain.blockchain.add(block2);*/
		
		String json = new GsonBuilder().setPrettyPrinting().create()
				.toJson(blockchain.getBlockchainIntanse().get(blockchain.getBlockchainIntanse().size() - 1));
		System.out.println("-------------");
		System.out.println(json);

		// create test transaction
		/*Transaction transaction = new Transaction(wallet.publicKey, wallet2.publicKey, (float) 5.1, null);
		transaction.generateSignature(wallet.privateKey);
		System.out.println("check signature: " + transaction.checkSignature());*/

		/*System.out.println("Start mining");
		while (true) {
			blockchain.miningBlock(5);
			String json = new GsonBuilder().setPrettyPrinting().create()
					.toJson(blockchain.getBlockchainIntanse().get(blockchain.getBlockchainIntanse().size() - 1));
			System.out.println("-------------");
			System.out.println(json);
			System.out.println("Chain valid ?" + blockchain.isChainValid());
		}*/

	}
}