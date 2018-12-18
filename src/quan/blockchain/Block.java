package quan.blockchain;

import java.util.Date;

public class Block {
	
	private String hash;
	private String data;
	private String previousHash;
	private Long timeStamp;
	private int nonce;
	
	
	public Block(String data, String previousHash) {
		super();
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
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



	public String toString() {
		return  "{ Hash of this block: " + this.hash + ",\n Previous hash: " + this.previousHash + ",\n Data: " + this.data + ", \n TimeStamp: " + this.timeStamp.toString()+"}";
	}

}
