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
	
	// return true if new transactionwas created
	public boolean processTransaction() {
		if(checkSignature() == false) {
			return false;
		}
		
		//gather transaction inputs
		if(transactionInput == null) {
			return false;
		} else {
		for (TransactionInput i : transactionInput) {
//			i.setUTXO(Main.UTXOs.get(i.transactionOutputId));
			i.UTXO = Main.UTXOs.get(i.transactionOutputId);
		}
		}
		// check if transaction is valid
		if(getInputValue() < Main.minimunTransaction) {
			System.out.println("Transaction input is small: " + getInputValue());
		}
		
		//create transaction output
		float leftOver = getInputValue() - value;
		transactionId = calculator();
		transactionOutput.add(new TransactionOutput(this.reciepient, value, transactionId)); //send value to the reciepient
		transactionOutput.add(new TransactionOutput(this.sender, leftOver, transactionId)); //send the left over change
		
		// add output to the Unspent List
		for (TransactionOutput o : transactionOutput) {
			Main.UTXOs.put(o.getId(), o);
		}
		
		// remove transaction input from UTXO was spent
		for (TransactionInput i : transactionInput) {
			if(i.UTXO == null) {
				continue;
			}
			Main.UTXOs.remove(i.UTXO.getId());
		}
		return true;
	}
	
	
	// return sum of input(UTXO) value
	public float getInputValue() {
		float total = 0;
		 for (TransactionInput i : transactionInput) {
			if(i.UTXO == null) {
				continue;
			}
			total += i.UTXO.getAmount();
		}
		return total;
	}
	
	// return sum of output value
	
	public float getOutputValue() {
		float total = 0;
		for (TransactionOutput o : transactionOutput) {
			total += o.getAmount();
		}
		return total;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
 