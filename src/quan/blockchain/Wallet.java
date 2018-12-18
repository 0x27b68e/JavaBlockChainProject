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

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
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
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public PrivateKey getPrivateKey() {
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
	}*/
	
}
