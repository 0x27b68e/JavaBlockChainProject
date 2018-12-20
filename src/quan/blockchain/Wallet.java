package quan.blockchain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); // only UTXO own this wallet
	// Setup Bouncey castle as a Security Provider
	static {
	    Security.addProvider(new BouncyCastleProvider());
	}
	public Wallet() {
		generateKeyPair();
	}
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecGenParameterSpec, random);
			
			KeyPair keyPair = keyGen.generateKeyPair();
			//set the public and private keys from the keyPair;
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// return balance ans store the UTXO's owned by this wallet in this.UTXO
	public float getBalance() {
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : BlockChain.UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			if(UTXO.isYourCoin(publicKey)) {
				UTXOs.put(UTXO.id, UTXO);
				total += UTXO.value;
			}
		}
		return total;
	}
	
	//Generate and return new transaction form this wallet
	public Transaction sendFund(PublicKey recieve, float value) {
		if(getBalance() < value) {
			System.out.println( "Not enough fund to send transaction. Stop");
			return null;
		}
		
		//create array list of inputs
		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		float total = 0;
		
		for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if(total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, recieve, value, inputs);
		newTransaction.generateSignature(privateKey);
		
		for (TransactionInput input : inputs) {
			UTXOs.remove(input.transactionOutputId);
		}
		return newTransaction;
	}
}
