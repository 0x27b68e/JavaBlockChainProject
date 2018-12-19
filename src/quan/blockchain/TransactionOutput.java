package quan.blockchain;

import java.security.PublicKey;

public class TransactionOutput {

	private String id;
	private PublicKey publicKey; // also know as the new owner of this coin
	private Float amount; // amout of coins they own
	private String parentTransactionId; //the id of the transaction this output was created in
	
	public TransactionOutput(PublicKey publicKey, Float amount, String parentTransactionId) {
		super();
		this.publicKey = publicKey;
		this.amount = amount;
		this.parentTransactionId = parentTransactionId;
	}
	//check if coin is belong to you?
	public boolean isYourCoin(PublicKey publicKey) {
		return this.publicKey == this.getPublicKey();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getParentTransactionId() {
		return parentTransactionId;
	}
	public void setParentTransactionId(String parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}
	
	
	
	
}
