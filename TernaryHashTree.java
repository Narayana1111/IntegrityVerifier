import java.util.ArrayList;
import java.util.List;

public class TernaryHashTree {
	private TernaryHashTreeNode root;

	public TernaryHashTree(byte[] rootHash) {
		this.root = new TernaryHashTreeNode(rootHash);
	}

	public TernaryHashTreeNode getRoot() {
		return root;
	}

	public byte[] getData() {
		List<byte[]> blockData = new ArrayList<>();
		traverseTree(root, blockData);
		return combineBlocks(blockData);
	}

	private void traverseTree(TernaryHashTreeNode node, List<byte[]> blockData) {
		if (node.isLeaf()) {
			blockData.add(node.getData());
		} else {
			traverseTree(node.getLeft(), blockData);
			traverseTree(node.getMiddle(), blockData);
			traverseTree(node.getRight(), blockData);
		}
	}

	private byte[] combineBlocks(List<byte[]> blockData) {
		int totalSize = 0;
		for (byte[] block : blockData) {
			totalSize += block.length;
		}

		byte[] combinedData = new byte[totalSize];
		int currentIndex = 0;
		for (byte[] block : blockData) {
			System.arraycopy(block, 0, combinedData, currentIndex, block.length);
			currentIndex += block.length;
		}

		return combinedData;
	}

	private static class TernaryHashTreeNode {
		private byte[] data;
		private TernaryHashTreeNode left;
		private TernaryHashTreeNode middle;
		private TernaryHashTreeNode right;

		public TernaryHashTreeNode(byte[] data) {
			this.data = data;
		}

		public boolean isLeaf() {
			return left == null && middle == null && right == null;
		}

		public byte[] getData() {
			return data;
		}

		public TernaryHashTreeNode getLeft() {
			return left;
		}

		public void setLeft(TernaryHashTreeNode left) {
			this.left = left;
		}

		public TernaryHashTreeNode getMiddle() {
			return middle;
		}

		public void setMiddle(TernaryHashTreeNode middle) {
			this.middle = middle;
		}

		public TernaryHashTreeNode getRight() {
			return right;
		}

		public void setRight(TernaryHashTreeNode right) {
			this.right = right;
		}
	}
}
