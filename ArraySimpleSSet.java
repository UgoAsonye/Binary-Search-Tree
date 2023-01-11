/**
 * <p>An implementation of the <code>SimpleSSet<code> interface using
 * an array to store the elements in the set. The elements are stored
 * in the array <code>contents</code> in ascending order according to
 * the natural ordering on <code>E</code>.</p>
 *
 * @see SimpleUSet
 * @author Will Rosenbaum
 */

public class ArraySimpleSSet<E extends Comparable<E>> implements SimpleSSet<E> {
    /**
     * <p>the default capacity of the array storing the set's contents</p>
     */
    public static final int DEFAULT_CAPACITY = 16;
    
    private int size = 0;
    private int capacity;

    private Object[] contents;

    /**
     */
    public ArraySimpleSSet() {
	this(DEFAULT_CAPACITY);
    }

    /**
     * <p>Create a new ArraySimpleUSet with a given initial capacity.</p>
     *
     * @param capacity the desired initial capacity > 0
     */
    public ArraySimpleSSet(int capacity) {
	this.capacity = capacity;
	contents = new Object[capacity];
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean add(E x) {

	// find the index to insert x
	int index = getIndex(x);

	// check if x is already there
	if (index < size && x.equals(contents[index])) {
	    return false;
	}

	// check if we need to increase the capacity
	if (size == capacity) {
	    increaseCapacity();
	}

	// insert the element at index
	Object cur = x;
	Object next;
	for (int i = index; i <= size; ++i) {
	    next = contents[i];
	    contents[i] = cur;
	    cur = next;
	}

	// update the size after successful insertion
	++size;

	return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(E x) {

	int index = getIndex(x);

	E y = null;

	if (index < size && x.equals(contents[index])) {
	    y = (E) contents[index];
	    removeAtIndex(index);
	}
	
	return y;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E find(E x) {

	int index = getIndex(x);

	E y = null;

	if (index < size) {
	    y = (E) contents[index];
	}

	return y;	
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findMin() {
	if (size == 0)
	    return null;

	return (E) contents[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findMax() {
	if (size == 0)
	    return null;

	return (E) contents[size - 1];
    }
    

    /**
     * Get the smallest index i such that x is less than contents[i]
     * in the natural ordering of E, or return size if no such index
     * exists. Note that in either case, the index returned is the
     * index at which x would be placed if x is added to the set.
     */
    @SuppressWarnings("unchecked")
    private int getIndex(E x) {
	// REMOVED: code for linear search
	// int index = 0;
	// while (index < size && x.compareTo((E) contents[index]) > 0) {
	//     ++index;
	// }

	int index = getIndex(x, 0, size);

	return index;
    }

    /*
     * Use binary search to find the smallest index containing y with
     * y > x under the natural ordering on E (defined by the compareTo
     * method). If no such y exists, this method returns iMax. The
     * running time of this method is O(1 + log (iMax - iMin)).
     */
    @SuppressWarnings("unchecked")
    private int getIndex(E x, int iMin, int iMax) {
	if (iMin == iMax) {
	    return iMin;
	}

	if (iMax - iMin == 1) {
	    // ternary operator
	    // equivalent to
	    // if (x.compareTo((E) contents[iMin]) > 0)
	    //     return iMax;
	    // else
	    //     return iMin;
	    return (x.compareTo((E) contents[iMin]) > 0) ? iMax : iMin;
	}
	
	int iMid = iMin + (iMax - iMin) / 2;

	if (x.compareTo((E) contents[iMid]) > 0) {
	    return getIndex(x, iMid, iMax);
	} else {
	    return getIndex(x, iMin, iMid);
	}
    }



    private void removeAtIndex(int j) {
	for (int i = j; i < size - 1; ++i) {
	    contents[i] = contents[i+1];
	}

	--size;
    }

    private void increaseCapacity() {
	Object[] bigContents = new Object[2 * capacity];
	
	for(int i = 0; i < capacity; ++i) {
	    bigContents[i] = contents[i];
	}

	contents = bigContents;
	capacity = 2 * capacity;
    }
}
