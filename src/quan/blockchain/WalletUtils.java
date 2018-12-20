package quan.blockchain;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;

public class WalletUtils {
	
	
	public static boolean getWallet(String walletName, HashMap<String, Wallet> listWallet) {
		  Set<String> keySet = listWallet.keySet();
		  boolean b = false;
		for (String entry : keySet) {
			if(walletName.equals(entry)) {
				b = true;
			}
		}
		return b;
	}
	
	public static void commandWallet(Wallet wallet) {
		Scanner scanner = new Scanner(System.in);
	    boolean b = true;
	    while (b) {
		System.out.println("Enter command to query (balance,) or q(exit): ");
		String key = scanner.nextLine();
		
		switch (key) {
		case "balance":
			System.out.println("Wallet balance: " + wallet.getBalance());
			break;
		case "q":
			System.out.println("exit.......");
			b = false;
			break;
		default:
			break;
			}
	    }
	
	}

}
