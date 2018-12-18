package quan.blockchain;

import java.security.MessageDigest;

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

}
