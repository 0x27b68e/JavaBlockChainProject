package quan.blockchain;

import java.security.PublicKey;

public class TransactionOutput {
	public String id;
	public PublicKey reciepient; // also know as the new owner of this coin
	public Float value; // amout of coins they own
	public String parentTransactionId; //the id of the transaction this output was created in
	
	public TransactionOutput(PublicKey reciepient, Float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = SHA256Helper.sha256Helper(SHA256Helper.getStringFromKey(reciepient)) + Float.toString(value) + parentTransactionId;
	}
	//check if coin is belong to you?
	public boolean isYourCoin(PublicKey publicKey) {
		return (this.reciepient == publicKey);
	}
	
	
}
