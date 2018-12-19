package quan.blockchain;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); // only UTXO own this wallet
	
	
	
	// Setup Bouncey castle as a Security Provider
	static {
	    Security.addProvider(new BouncyCastleProvider());
	}
	public Wallet() {
		// TODO Auto-generated constructor stub
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
			//publicKey = keyPair.getPublic();
			//privateKey = keyPair.getPrivate();
			setPublicKey(keyPair.getPublic());
			setPrivateKey(keyPair.getPrivate());
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException  e) {
			e.printStackTrace();
		}
	}
	// return balance ans store the UTXO's owned by this wallet in this.UTXO
	public float getBalance() {
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : Main.UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			if(UTXO.isYourCoin(publicKey)) {
				UTXOs.put(UTXO.getId(), UTXO);
				total += UTXO.getAmount();
			}
		}
		return total;
	}
	
	//Generate and return new transaction form this wallet
	public Transaction sendFund(PublicKey recieve, float amout) {
		if(getBalance() < amout) {
			System.out.println( "Not enough fund to send transaction. Stop");
			return null;
		}
		
		
		List<TransactionInput> inputs = new ArrayList<TransactionInput>();
		float total = 0;
		
		for (Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.getAmount();
			inputs.add(new TransactionInput(UTXO.getId()));
			if(total > amout) {
				break;
			}
			
		}
		
		Transaction newTransaction = new Transaction(this.getPublicKey(), recieve, amout, inputs);
		newTransaction.generateSignature(getPrivateKey());
		
		for (TransactionInput item : inputs) {
			inputs.remove(item.transactionOutputId);
		}
		return newTransaction;
		
	}
	
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
}
