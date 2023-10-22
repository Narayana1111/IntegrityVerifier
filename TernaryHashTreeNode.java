public class TernaryHashTreeNode {
	private byte[] hash;
	private TernaryHashTreeNode left;
	private TernaryHashTreeNode middle;
	private TernaryHashTreeNode right;

	// Constructor
	public TernaryHashTreeNode(byte[] hash) {
		this.hash = hash;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
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
