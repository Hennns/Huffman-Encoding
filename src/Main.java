import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	static int totalBitCounterHuffman = 0;
	static int totalBitCounterText = 0;

	public static void main(String[] args) {
		final int bitsForTextChar = 3; // set this value to 8 for the first test
		//first test file			
		// ArrayList<Tracker> data = readFile("src/The Taste of Melon.txt");

		// Second test file
		ArrayList<Tracker> data = readFile("src/test.txt");
		char[] charArray = new char[data.size()];
		int[] charfreq = new int[data.size()];

		for (int i = 0; i < data.size(); i++) {
			charArray[i] = data.get(i).getCharacter();
			charfreq[i] = data.get(i).getCounter();
		}

		// These are used for testing
		// char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f'};
		// int[] charfreq = { 12, 2, 7, 13, 14, 85};
		int length = charArray.length;

		PriorityQueue<Node> heap = new PriorityQueue<Node>(length, new CompareNode());

		// this creates a heap
		for (int i = 0; i < length; i++) {
			totalBitCounterText += charfreq[i] * bitsForTextChar;
			System.out.println(charArray[i] + " appear " + charfreq[i] + " times");
			Node nextNode = new Node(charArray[i], charfreq[i]);
			heap.add(nextNode);
		}

		while (heap.size() > 1) {
			Node nodeA = heap.poll();
			Node nodeB = heap.poll();
			Node root = new Node((nodeA.getWeight() + nodeB.getWeight()), nodeA, nodeB);
			System.out.println(root.getWeight());
			heap.add(root);
		}

		System.out.println("Letter | Code");
		// The heap is empty after this line, the last Node is the root node.
		printCodes(heap.poll(), "");

		System.out.println("Assuming each char takes " + bitsForTextChar + " bits");
		System.out.println("The text was " + totalBitCounterText + " bits");
		System.out.println("With Huffman Encoding we use " + totalBitCounterHuffman + " bits");
		float percent = (totalBitCounterText - totalBitCounterHuffman) * 100f / (totalBitCounterText);
		System.out.println("That is " + (totalBitCounterText - totalBitCounterHuffman) + " bits less");
		System.out.println("That is " + percent + " % decrease");
	}

	
	
	// traverse the tree to find the codes
	private static void printCodes(Node n, String code) {
		if (n.isLeaf()) {
			System.out.println("'" + n.getCharacter() + "'" + code);
			// System.out.println((int)n.getCharacter());
			totalBitCounterHuffman += code.length() * n.getWeight();
			// System.out.println("'" + n.getCharacter() + "' takes up
			// "+n.getWeight()*code.length()+" bits");
			return;
		}
		printCodes(n.getLeftChild(), code + "0");
		printCodes(n.getRigthChild(), code + "1");
	}

	// reads given file
	private static ArrayList<Tracker> readFile(String path) {
		File file = new File(path);
		ArrayList<Tracker> in = new ArrayList<Tracker>();

		String s = "";
		try {
			Scanner sc = new Scanner(file);
			s = sc.useDelimiter("\\Z").next();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// We put it as an array of characters to easier sort and count them
		char tempArray[] = s.toCharArray();
		Arrays.sort(tempArray);

		// add the first character so we can compare to something
		in.add(new Tracker(tempArray[0]));
		int counter = 0;
		// we start looping at 1 since we already added the first character
		for (int t = 1; t < tempArray.length; t++) {
			if (in.get(counter).getCharacter() == (tempArray[t])) {
				in.get(counter).increment();
				// System.out.println(in.get(counter).getCharacter() + " have
				// appeared " + in.get(counter).getCounter() + " times");
			} else {
				// System.out.println("added "+tempArray[t]);
				in.add(new Tracker(tempArray[t]));
				counter++;
			}
		}
		return in;
	}

}

// we use this to compare the Nodes in the heap
class CompareNode implements Comparator<Node> {
	public int compare(Node x, Node y) {
		return x.getWeight() - y.getWeight();
	}
}

class Tracker {
	private char c;
	private int counter;

	public Tracker(char letter) {
		c = letter;
		counter = 1;
	}

	public void increment() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}

	public char getCharacter() {
		return c;
	}

}
