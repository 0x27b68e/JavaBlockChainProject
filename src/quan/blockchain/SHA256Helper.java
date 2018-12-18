package quan.blockchain;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA256Helper {
	
	public static String sha256Helper(String string) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] stringToBytes = string.getBytes("UTF-8");
			byte[] hash = md.digest(stringToBytes);
			
			for (int i = 0; i < hash.length; i++) {
				String hexString = Integer.toHexString(0xff & hash[i]);
				if(hexString.length() == 1) {
					stringBuffer.append(0);
				}
				stringBuffer.append(hexString);
			}
			return stringBuffer.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
	
	public static String sha256CommonDecHelper(String string) {
		return DigestUtils.sha256Hex(string);
	}
	
	// applies ECDSA Signature and returns the result as byte[]
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature signature;
		byte[] output = new byte[0];
		
		try {
			 signature = Signature.getInstance("ECDSA", "BC");//create Signature object
			 signature.initSign(privateKey); // init Sign privateKey
			 signature.update(input.getBytes()); // and update input
			 
			 output = signature.sign(); // sign done!
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	// verifies the String signature
	public static boolean verifyECDSASign(PublicKey publicKey, String data, byte[] signature)  {
		Signature ecdsaVerify;
		boolean check = false;
		
			try {
				ecdsaVerify  = Signature.getInstance("ECDSA", "BC");
				ecdsaVerify .initVerify(publicKey);
				ecdsaVerify .update(data.getBytes());
				check = ecdsaVerify.verify(signature);
			} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return check;
	}
	
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

}
