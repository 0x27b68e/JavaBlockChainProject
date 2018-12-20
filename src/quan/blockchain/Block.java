package quan.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Block {
	
	public String hash;
	public String previousHash;
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public Long timeStamp;
	public int nonce;
	
	
	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		return SHA256Helper.sha256Helper(
				previousHash + 
				Long.toString(this.timeStamp) + 
				Integer.toString(nonce) +
				merkleRoot
				);
	}
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = SHA256Helper.getMerkleRoot(transactions);
		String target = SHA256Helper.getDiffString(difficulty);
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
	}
	
	// add transaction to this Block
	public boolean addTransaction(Transaction transaction) {
		// check if Transaction is null or not, check if Generic block is ignore
		if(transaction == null) return false;
		//if(this.previousHash != "0") 
		if(!"0".equals(previousHash)) 
			if(transaction.processTransaction() != true) {
				System.out.println("Process transaction fail!");
				return false;
			}
		
		transactions.add(transaction);
		System.out.println("Transaction add ok!!");
		return true;
	}
}
