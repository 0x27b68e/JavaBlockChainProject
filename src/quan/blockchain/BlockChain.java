package quan.blockchain;

import java.util.ArrayList;
import java.util.List;



public class BlockChain {
	
	public static List<Block> blockchain = new ArrayList<Block>();
	
	public BlockChain() {
//		blockchain.add(new Block("This is first Block", "0"));
	}
	
	public void miningBlock(int difficulty) {
		Block block = new Block("found new block " + blockchain.size(),lastBlock().getHash());
		block.mineBlock(difficulty);
		blockchain.add(block);
		
	}
	//get last Block
	 public Block lastBlock() {
			return blockchain.get(blockchain.size()-1);
	}
	
	
	public static List<Block> getBlockchainIntanse() {
		return blockchain;
	}
	
	public static boolean isChainValid() {
	Block  currentBlock;
	Block previousBlock;
	for (int i = 1; i < blockchain.size(); i++) {
		if(i == 4) {
			blockchain.get(i).setData("Spam data"); //sau block nay, blockchain is not valid
		}
		currentBlock = blockchain.get(i);
		previousBlock = blockchain.get(i-1);
		if(!currentBlock.getHash().equals(currentBlock.calculateHash())) { // nếu thay đổi hash ở Block nào đó, thì currentBlock.calculateHash() sẽ thay đổi và sẽ khác currentBlock.hash() đã lưu trong blockChain
				System.out.println("current hash is not equals");
			return false;
		}
		
		if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
			System.out.println("previous hash is not equals");
			return false;
		}
	}
	return true;
}

}
