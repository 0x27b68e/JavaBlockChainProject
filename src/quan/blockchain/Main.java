package quan.blockchain;


import com.google.gson.GsonBuilder;

public class Main {

	public static void main(String[] args) {
		BlockChain blockchain =  new BlockChain();
		System.out.println("Start mining");
		
		while(true) {
			blockchain.miningBlock(5);
			String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain.getBlockchainIntanse());
			System.out.println("-------------");
			System.out.println(json);
			System.out.println("Chain valid ?" + blockchain.isChainValid());
		}
		/*Block genericBlock  = new Block("This is first Block1", "0");
		Block secondBlock = new Block("This is second Block", genericBlock.getHash());
		Block thirdBock = new Block("This is Third Block", secondBlock.getHash());
		
		//System.out.println("check valid block chain: " + isChainValid());
		 * 
		 */
		
	}
}