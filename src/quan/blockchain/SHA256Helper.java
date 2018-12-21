package quan.blockchain;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.GsonBuilder;

public class SHA256Helper {
	
	public static String sha256Helper(String input) {
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
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
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return output;
		}
		
		// verifies the String signature
		public static boolean verifyECDSASign(PublicKey publicKey, String data, byte[] signature)  {
				try {
					Signature ecdsaVerify  = Signature.getInstance("ECDSA", "BC");
					ecdsaVerify .initVerify(publicKey);
					ecdsaVerify .update(data.getBytes());
					return  ecdsaVerify.verify(signature);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
		}
		public static String getJson(Object o) {
			return new GsonBuilder().setPrettyPrinting().create().toJson(o);
		}
		
		//Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"  
		public static String getDiffString(int difficulty) {
			String target = "";
			for (int i = 0; i < difficulty; i++) {
				String c = "0";
				target = target.concat(c);
			}
			return target;
		}
		
		public static String getStringFromKey(Key key) {
			return Base64.getEncoder().encodeToString(key.getEncoded());
		}
		
	// Tacks in array of transactions and returns a merkle root.
		public static String getMerkleRoot(ArrayList<Transaction> transactions) {
			int count = transactions.size();
			
			ArrayList<String> previousTreeLayer = new ArrayList<String>();
			for (Transaction transaction : transactions) {
				previousTreeLayer.add(transaction.transactionId);
			}
			ArrayList<String> treeLayer = previousTreeLayer;
			while (count > 1) {
				treeLayer = new ArrayList<String>();
				for (int i = 1; i < previousTreeLayer.size(); i++) {
					treeLayer.add(sha256Helper(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
				}
				count = treeLayer.size();
				previousTreeLayer = treeLayer;
			}
			String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
			return merkleRoot;
		}
	
	/*public static String getMerkleRoot(List<Transaction> transactions) {
		List<String> transactionIdList = new ArrayList<String>();
		for (Transaction string : transactions) {
			transactionIdList.add(string.transactionId);
		}
		List<String> hashList = convertFromTransactionToHash(transactionIdList);
		// đệ qui
		while (hashList.size() != 1) {
			hashList = convertFromTransactionToHash(hashList);
		}
		return hashList.get(0); 
	}
	
    private static List<String> convertFromTransactionToHash(List<String> listTransaction) {
		
		List<String> hashList = new ArrayList<String>();
		int index = 0;
		while (index < listTransaction.size()) {
			//left
			String left = listTransaction.get(index);
			index++;
			// right
			String right = listTransaction.get(index);
			// sha hash value
			String hash = sha256Helper(left + right);
			hashList.add(hash);
			index++;
		}
		return hashList;
	}

	
	public static String sha256CommonDecHelper(String string) {
		return DigestUtils.sha256Hex(string);
	}*/

}
