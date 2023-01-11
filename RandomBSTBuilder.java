import java.util.Random;

public class RandomBSTBuilder {
    public static final int MAX = 1_000_000;
    public static final int STEP = 1_000;

    public static void main(String[] args) {
	Random rand = new Random();
	CSVWriter csv = new CSVWriter("tree-heights.csv");
	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

	csv.addEntry("set size");
	csv.addEntry("height");
	csv.endLine();

	int count = 0;

	while (count < MAX) {
	    if (bst.add(rand.nextInt())) {
		++count;
	    }

	    if (count % STEP == 0) {
		csv.addEntry(count);
		csv.addEntry(bst.height());
		csv.endLine();
	    }
	}

	csv.close();
    }
}
