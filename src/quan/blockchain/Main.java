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
		/*
		if(!checkWalletName) {
			System.out.println("Do you want to create new wallet: ");
			String string  = scanner.nextLine();
			if(string.equals("y")) {
				BlockChain.createNewWalet(walletName);
				Wallet wallet = BlockChain.getWallet(walletName);
				WalletUtils.commandWallet(wallet);
			}
		} else  {
			Wallet wallet = BlockChain.getWallet(walletName);
			WalletUtils.commandWallet(wallet);
		}*/
		
		

		/*System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFund(walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFund(walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());*/
		
		/*Block block2 = new Block("Block 3", block1.getHash());
		System.out.println("WalletB sent 5 coin to WalletA: ");
		block2.addTransaction(walletB.sendFund(walletA.publicKey, 0));
		blockchain.blockchain.add(block2);*/
		
		/*String json = new GsonBuilder().setPrettyPrinting().create()
				.toJson(blockchain.getBlockchainIntanse().get(blockchain.getBlockchainIntanse().size() - 1));
		System.out.println("-------------");
		System.out.println(json);*/

		// create test transaction
		/*Transaction transaction = new Transaction(wallet.publicKey, wallet2.publicKey, (float) 5.1, null);
		transaction.generateSignature(wallet.privateKey);
		System.out.println("check signature: " + transaction.checkSignature());*/

		/*System.out.println("Start mining");
		while (true) {
			blockchain.miningBlock(5);
			String json = new GsonBuilder().setPrettyPrinting().create()
					.toJson(blockchain.getBlockchainIntanse().get(blockchain.getBlockchainIntanse().size() - 1));
			System.out.println("-------------");
			System.out.println(json);
			System.out.println("Chain valid ?" + blockchain.isChainValid());
		}*/
}