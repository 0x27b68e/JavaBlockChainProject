package quan.blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

	public String transactionId;
	public PublicKey sender;
	public PublicKey reciepient;
	public Float value;

	public byte[] signature;

	List<TransactionInput> transactionInput = new ArrayList<TransactionInput>();
	List<TransactionOutput> transactionOutput = new ArrayList<TransactionOutput>();

	public static int sequence = 0;

	public Transaction(PublicKey sender, PublicKey reciepient, Float value, List<TransactionInput> transactionInput) {
		super();
		this.sender = sender;
		this.reciepient = reciepient;
		this.value = value;
		this.transactionInput = transactionInput;
	}

	// create signature
	public void generateSignature(PrivateKey privateKey) {
		String data = SHA256Helper.getStringFromKey(sender) + SHA256Helper.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = SHA256Helper.applyECDSASig(privateKey, data);
	}

	// check signature
	public boolean checkSignature() {
		String data = SHA256Helper.getStringFromKey(sender) + SHA256Helper.getStringFromKey(reciepient)
		+ Float.toString(value);
		return SHA256Helper.verifyECDSASign(sender, data, signature);
	}
	
	
	private String calculator() {
		sequence++;
		return SHA256Helper.sha256CommonDecHelper(SHA256Helper.getStringFromKey(sender)
				+ SHA256Helper.getStringFromKey(reciepient) + Float.toString(value) + sequence);
	}

}
