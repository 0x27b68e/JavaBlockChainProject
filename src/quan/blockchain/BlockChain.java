package quan.blockchain;

import java.util.ArrayList;
import java.util.List;



public class BlockChain {
	
	public static List<Block> blockchain = new ArrayList<Block>();
	
	public BlockChain() {
		blockchain.add(new Block("This is first Block", "0"));
	}
	
	public void miningBlock(int difficulty) {
		Block block = new Block();
		String hash = block.mineBlock(difficulty);
		block.setHash(hash);
		block.setPreviousHash(lastBlock().getHash());
		block.setData("found new block " + blockchain.size());
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
		if(i ==1) {
			//blockchain.set(i, new Block("spam", blockchain.get(i-1).getPreviousHash())); // change previous hash=> is not valid blockchain
			//blockchain.get(i).setHash("d70db700b32195abbb4e15eff4b6b74bf3f8109ae4998caa38bc793a4c1f3023"); //change current hash => is not valid blockchain
			//blockchain.get(i).setData("spam data"); // change data and current hash is not valid
			//blockchain.get(i).setPreviousHash("d70db700b32195abbb4e15eff4b6b74bf3f8109ae4998caa38bc793a4c1f3023");// change PreviousHash in this block => current hash is not valid
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
