package quan.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
	
	private String hash;
	private String data;
	private String previousHash;
	private Long timeStamp;
	private int nonce;
	
	private String merkleRoot;
	public List<Transaction> transactions = new ArrayList<Transaction>();
	
	public Block(String data, String previousHash) {
		super();
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	// add transaction to this Block
	public boolean addTransaction(Transaction transaction) {
		// check if Transaction is null or not, check if Generic block is ignore
		if(transaction == null) return false;
		if(this.previousHash != "0") 
			if(transaction.processTransaction() != true) {
				System.out.println("Process transaction fail!");
				return false;
			}
		
		transactions.add(transaction);
		System.out.println("Transaction add ok!!");
		return true;
	}
	
	public void mineBlock(int difficulty) {
		String target = "";
		for (int i = 0; i < difficulty; i++) {
			String c = "0";
			target = target.concat(c);
		}
		while(!this.hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			this.hash = calculateHash();
		}
		System.out.println("found new block");
	}
	
	public String calculateHash() {
		return SHA256Helper.sha256Helper(this.data + this.previousHash + Long.toString(this.timeStamp) + Integer.toString(nonce));
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public int getNonce() {
		return nonce;
	}



	public void setNonce(int nonce) {
		this.nonce = nonce;
	}



	public String getMerkleRoot() {
		return merkleRoot;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public String toString() {
		return  "{ Hash of this block: " + this.hash + ",\n Previous hash: " + this.previousHash + ",\n Data: " + this.data + ", \n TimeStamp: " + this.timeStamp.toString()+"}";
	}

}
