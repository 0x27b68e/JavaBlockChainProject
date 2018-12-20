package quan.blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

	public String transactionId; // this is a hash of this transaction
	public PublicKey sender;     // sender address/ public key
	public PublicKey reciepient; // reciepient address/public key
	public Float value;			// amount of coins 
	public byte[] signature;    // this is to prevent anyone else from spending funds from our wallet.

	public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	public static int sequence = 0; // count of how many transactions have been generated.

	public Transaction(PublicKey sender, PublicKey reciepient, Float value, List<TransactionInput> inputs) {
			this.sender = sender;
			this.reciepient = reciepient;
			this.value = value;
			this.inputs = inputs;
	}
	
	// return true if new transaction was created
		public boolean processTransaction() {
			if(checkSignature() == false) {
				System.out.println("#Transaction Signature failed to verify");
				return false;
			}
			
			//gather transaction inputs
			for (TransactionInput i : inputs) {
				i.UTXO = BlockChain.UTXOs.get(i.transactionOutputId);
			}
			// check if transaction is valid
			if(getInputsValue() < Main.minimunTransaction) {
				System.out.println("Transaction input is small: " + getInputsValue());
				System.out.println("Please enter the amount greater than " + Main.minimunTransaction);
				return false;
			}
			
			//create transaction output
			float leftOver = getInputsValue() - value;
			transactionId = calculator();
			outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); //send value to the reciepient
			outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); //send the left over change
			
			// add outputs to the Unspent List
			for (TransactionOutput o : outputs) {
				BlockChain.UTXOs.put(o.id, o);
			}
			
			// remove transaction input from UTXO was spent
			for (TransactionInput i : inputs) {
				if(i.UTXO == null) {
					continue;
				}
				BlockChain.UTXOs.remove(i.UTXO.id);
			}
			return true;
		}
		
		// return sum of input(UTXO) value
		public float getInputsValue() {
			float total = 0;
			 for (TransactionInput i : inputs) {
				if(i.UTXO == null) {
					continue;
				}
				total += i.UTXO.value;
			}
			return total;
		}
	
	// create signature
	public void generateSignature(PrivateKey privateKey) {
		String data = SHA256Helper.getStringFromKey(sender) + SHA256Helper.getStringFromKey(reciepient) + Float.toString(value);
		signature = SHA256Helper.applyECDSASig(privateKey, data);
	}

	// check signature
	public boolean checkSignature() {
		String data = SHA256Helper.getStringFromKey(sender) + SHA256Helper.getStringFromKey(reciepient) + Float.toString(value);
		return SHA256Helper.verifyECDSASign(sender, data, signature);
	}
	
	 public float getOutputValue() {
			float total = 0;
			for (TransactionOutput o : outputs) {
				total += o.value;
			}
			return total;
		}
	 
	// create transaction hash, as ID of this transaction
		private String calculator() {
			sequence++;
			return SHA256Helper.sha256Helper(
					SHA256Helper.getStringFromKey(sender) + 
					SHA256Helper.getStringFromKey(reciepient) + 
					Float.toString(value) + sequence
					);
		}
		
}
 