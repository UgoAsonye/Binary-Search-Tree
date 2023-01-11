import java.util.Arrays;
import java.util.Random;

public class BSTTester {

    private static int MAX = 20;
    private static Random rand = new Random(0);

    private static final int[] VALS = {7, 15, 4, 13, 2, 5, 11, 17, 9};
    private static final int[] HEIGHTS = {0, 1, 1, 2, 2, 2, 3, 3, 4};
    
    public static void main(String[] args) {
	
	// replace with your SimpleUSet implementation
	BinarySearchTree<Integer> set = new BinarySearchTree<Integer>();
	
	System.out.println("Constructing array of elements...");

	boolean[] inSet = getInSet();
	int[] elts = getElementArray(inSet);
	int[] order = getElementOrder();

	System.out.println("    Elements = " + Arrays.toString(elts));

	System.out.println("Testing add method...");
	if (testAdd(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing remove method...");
	if (testRemove(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}
	
	System.out.println("Testing find method...");
	if (testFind(set, inSet, order)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}

	System.out.println("Testing height method...");
	set = new BinarySearchTree<Integer>();
	if (testHeight(set)) {
	    System.out.println("   ...test passed");
	} else {
	    System.out.println("   ...test failed");
	}	
    }

    private static boolean[] getInSet () {
	boolean[] inSet = new boolean[MAX];
	for (int i = 0; i < inSet.length; ++i) {
	    inSet[i] = (rand.nextInt(2) == 0);
	}

	return inSet;
    }

    private static int[] getElementArray (boolean[] inSet) {
	int size = 0;
	for (int i = 0; i < inSet.length; ++i) {
	    if (inSet[i]) {
		++size;
	    }
	}

	int[] elts = new int[size];

	int curIndex = 0;

	for (int i = 0; i < inSet.length; ++i) {
	    if (inSet[i]) {
		elts[curIndex] = i;
		++curIndex;
	    }
	}

	return elts;
    }

    private static int[] getElementOrder() {
	int[] order = new int[MAX];
	
	for(int i = 0; i < MAX; ++i) {
	    order[i] = i;
	}

	for (int i = 1; i < MAX; ++i) {
	    int j = rand.nextInt(i+1);
	    int val = order[j];
	    order[j] = order[i];
	    order[i] = val;
	}

	return order;
	     
    }

    private static boolean testAdd (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {
	int size = 0;

	// try adding elements to the set
	for (int i : order) {
	    if (inSet[i]) {
		if (!set.add(i)) {
		    System.out.println("   add(" + i + ") failed.");
		    return false;
		}
		
		++size;
	    }
	}

	if (set.size() != size) {
	    System.out.println("   size() method returned incorrect value");

		return false;
	}

	for (int i = 0; i < inSet.length; ++i) {
	    boolean added = set.add(i);
	    if (added && inSet[i]) {
		System.out.println("   added " + i + " which was already in set");
		return false;
	    }

	    if (!added && !inSet[i]) {
		System.out.println("   failed to add " + i + " (not previously in set)");
		return false;
	    }
	}
	
	return true;
    }

    private static boolean testRemove (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {
	int size = inSet.length;
	
	for (int i : order) {
	    if (!inSet[i]) {
		if (set.remove(i) == null) {
		    System.out.println("   failed to remove " + i);
		    return false;
		}
		--size;
	    }
	}

	if (size != set.size()) {
	    System.out.println("   size() method fails after removal");
	    return false;
	}
	
	return true;
    }

    private static boolean testFind (SimpleUSet<Integer> set, boolean[] inSet, int[] order) {	
	for (int i : order) {
	    //System.out.println("    finding " + i);
	    
	    Integer elt = set.find(i);

	    if (inSet[i] && (!elt.equals(i))) {
		System.out.println("   failed to find " + i);
		return false;
	    }

	    if (!inSet[i] && (elt != null && elt.equals(i))) {
		System.out.println("   found " + i + " but should not have");
		return false;
	    }
	}
	
	return true;
    }

    private static boolean testHeight(BinarySearchTree<Integer> set) {
	if (set.height() != -1) {
	    System.out.println("  empty tree should have height -1");
	    return false;
	}
	
	System.out.print("  adding elements ");

	for (int i = 0; i < VALS.length; ++i) {
	    System.out.print(VALS[i] + " ");
	    set.add(VALS[i]);
	    int height = set.height();
	    if (height != HEIGHTS[i]) {
		System.out.println("\n   returned height " + height + " but should have been " + HEIGHTS[i]);
		return false;
	    }
	}

	System.out.println("");

	return true;
    }
    
}
