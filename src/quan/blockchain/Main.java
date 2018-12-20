package quan.blockchain;

import java.awt.peer.SystemTrayPeer;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;
	public static float minimunTransaction = 0.1f;
	
	public static BlockChain blockChain = new BlockChain();
	public static HashMap<String, Wallet> listWallet = new HashMap<>();
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		
		// Setup Bouncey castle as a Security Provider
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		Wallet walletA = BlockChain.getWallet("walletA");
		System.out.println("Wallet A balance: " + walletA.getBalance());
		
		Wallet coinbase = BlockChain.getWallet("coinbase");
		System.out.println("coinbase Wallet balance: " + coinbase.getBalance());
		
		BlockChain.listTransaction.add(coinbase.sendFund(walletA.publicKey, 40f));
		blockChain.miningBlock(difficulty);
		System.out.println("Wallet A balance: " + walletA.getBalance());
		
		Wallet walletB = BlockChain.getWallet("walletB");
		BlockChain.listTransaction.add(walletA.sendFund(walletB.publicKey, 10f));
		blockChain.miningBlock(difficulty);
		System.out.println("Balance WalletA: " + walletA.getBalance());
		System.out.println("Balance WalletB: " + walletB.getBalance());
		
		
		System.out.println("Start mining!!");
		while (true) {
						System.out.println("do you want to transact, y/n");
						String choise = scanner.nextLine();
					
						if(choise.equals("y")) {
						System.out.println("what name of your wallet! ");
						String walletName = scanner.nextLine();
						Wallet wallet = BlockChain.getWallet(walletName);

						System.out.println("wallet name you want to send to ");
						String reciepient = scanner.nextLine();
						Wallet walletRecieve = BlockChain.getWallet(reciepient);

						System.out.println("How many coins do you want to send :");
						float value = scanner.nextFloat();
						BlockChain.listTransaction.add(wallet.sendFund(walletRecieve.publicKey, value));
						}
					blockChain.miningBlock(difficulty);
					Wallet wallet = BlockChain.getWallet("walletB");
					System.out.println("walletB: " + wallet.getBalance());
					System.out.println();
		}
	}			
}