package quan.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class BlockChain {
	
	public static List<Block> blockchain = new ArrayList<Block>();
	public static Transaction genericTransaction;
	public static Map<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();
	public static Block generic;
	
	public static HashMap<String,Wallet> walletList  = new HashMap<String, Wallet>();
	public static List<Transaction> listTransaction = new ArrayList<>();
	static {
		walletList.put("walletA", new Wallet());
		walletList.put("walletB", new Wallet());
	}
	
	public BlockChain() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		 walletList.put("coinbase", new Wallet());
		
		//create genesis transaction,
		genericTransaction = new Transaction(walletList.get("coinbase").publicKey, walletList.get("coinbase").publicKey, 100f, null);
		// signature for genericTransaction address
		genericTransaction.generateSignature(walletList.get("coinbase").privateKey);
		genericTransaction.transactionId = "0"; // manual set transactionId
		// manual add the TransactionOutput
		genericTransaction.outputs.add(new TransactionOutput(genericTransaction.reciepient,
		genericTransaction.value, genericTransaction.transactionId));
		// it is important to store our first transaction in the UTXOs list
		UTXOs.put(genericTransaction.outputs.get(0).id, genericTransaction.outputs.get(0));
		System.out.println("Creating the generic block");
		generic = new Block("0");
		generic.addTransaction(genericTransaction);
		
		//
		System.out.println("Start mining generis Block !");
		generic.mineBlock(5);
		blockchain.add(generic);
	}
	
	//create new Wallet
	public static void createNewWalet(String walletName) {
		walletList.put(walletName, new Wallet());
	}
	
	// create Wallet
	public static Wallet getWallet(String walletName) {
		Set<String> keySet = walletList.keySet();
		Wallet wallet = null;
		for (String string : keySet) {
			if(string.equals(walletName)) {
				wallet = walletList.get(string);
			}
		}
		return wallet;
	}
	
	
	
	public void miningBlock(int difficulty) {
		Block block = new Block(lastBlock().hash);
		if(listTransaction.size() == 0) {
		block.mineBlock(difficulty);
		blockchain.add(block);
		System.out.println("found Block " + (blockchain.size() - 1) + " Hash: " + block.hash);	 
		} else {
			for (Transaction transaction : listTransaction) {
				block.addTransaction(transaction);	
			}
			block.mineBlock(difficulty);
			blockchain.add(block);
		}
	}

	//get last Block
	 public Block lastBlock() {
			return blockchain.get(blockchain.size()-1);
	}
	 
	public boolean isChainValid() {
	Block  currentBlock;
	Block previousBlock;
	for (int i = 1; i < blockchain.size(); i++) {
		if(i == 4) {
			blockchain.get(i).hash = "Spam data"; //sau block nay, blockchain is not valid
		}
		currentBlock = blockchain.get(i);
		previousBlock = blockchain.get(i-1);
		if(!currentBlock.hash.equals(currentBlock.calculateHash())) { // nếu thay đổi hash ở Block nào đó, thì currentBlock.calculateHash() sẽ thay đổi và sẽ khác currentBlock.hash() đã lưu trong blockChain
				System.out.println("current hash is not equals");
			return false;
		}
		
		if(!currentBlock.previousHash.equals(previousBlock.hash)) {
			System.out.println("previous hash is not equals");
			return false;
		}
	}
	return true;
}

}
