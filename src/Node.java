
public class Node {

	private final int weigth;
	private final char c;

	private final Node leftChild;
	private final Node rigthChild;

	public Node(char letter, int value) {
		c = letter;
		weigth = value;
		leftChild = null;
		rigthChild = null;
	}

	public Node(int value, Node left, Node rigth) {
		c = '\0';
		weigth = value;
		leftChild = left;
		rigthChild = rigth;
	}

	public int getWeight() {
		return weigth;
	}

	public char getCharacter() {
		return c;
	}

	public Node getRigthChild() {
		return rigthChild;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public boolean isLeaf() {
		if (leftChild == null && rigthChild == null) {
			return true;
		}
		return false;
	}

}
