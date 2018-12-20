package quan.blockchain;


/*An input is a reference to an output from a previous transaction. Multiple inputs are often listed in a transaction
All of the new transaction's input values (that is, the total coin value of the previous outputs referenced by the new transaction's inputs) are added up
refer : https://en.bitcoin.it/wiki/Transaction 
*/
public class TransactionInput {
	
	public String transactionOutputId; //Reference to TransactionOutputs
	public TransactionOutput UTXO; // Contains the Unspent Transaction Output
	
	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
	

}
