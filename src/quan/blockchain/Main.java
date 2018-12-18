package quan.blockchain;


import com.google.gson.GsonBuilder;

public class Main {

	public static void main(String[] args) {
		BlockChain blockchain =  new BlockChain();
		Wallet wallet = new Wallet();
		//test wallet, dont know why public key wallet1 is same wallet2 ???
		System.out.println("Public key wallet1: " + SHA256Helper.getStringFromKey(wallet.publicKey));
		System.out.println("Private key wallet1: " + SHA256Helper.getStringFromKey(wallet.privateKey));
		
		Wallet wallet2 = new Wallet();
		System.out.println("Public key wallet2: " + SHA256Helper.getStringFromKey(wallet2.publicKey));
		System.out.println("Private key wallet2: " + SHA256Helper.getStringFromKey(wallet2.privateKey));

		
		// create test transaction
		Transaction transaction = new Transaction(wallet.publicKey, wallet2.publicKey, (float) 5.1, null);
		transaction.generateSignature(wallet.privateKey);
		System.out.println("check signature: " + transaction.checkSignature());
		
		System.out.println("Start mining");
		while(true) {
			blockchain.miningBlock(5);
			String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain.getBlockchainIntanse().get(blockchain.getBlockchainIntanse().size()-1));
			System.out.println("-------------");
			System.out.println(json);
			System.out.println("Chain valid ?" + blockchain.isChainValid());
		}
		
	}
}