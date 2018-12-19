package quan.blockchain;


//This class is used to refer Unspent TransactionOutput. transactionOutputId is used to relatived TransactionOutput.
public class TransactionInput {
	
	public String transactionOutputId;
	public TransactionOutput UTXO;
	
	
	
	public TransactionInput(String transactionOutputId) {
		super();
		this.transactionOutputId = transactionOutputId;
	}
	

}
