import java.sql.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Jadon Co
 * @version 1.0
 * @userid jco9
 * @GTID 903725118
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable<?>[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new java.lang.IllegalArgumentException("The dataset is null or an element in said dataset in null");
        }
        backingArray = (T[]) new Comparable[((2 * (data.size())) + 1)];


        for (int i = 0; i < data.size(); i++) {
            backingArray[i + 1] = data.get(i);
            size++;
        }

        for (int i = (size / 2); i > 0; i--) {
            downHeapHelper(i);
        }
    }



    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("the dataset trying to be added to the Heap is null");
        }
        if (size + 1 >= (backingArray.length)) {
            T[] newArray = (T[]) new Comparable[2 * (backingArray.length)];
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }

        if (size == 0) {
            backingArray[1] = data;
        } else {
            backingArray[size + 1] = data;
            upHeapHelper(size + 1);

        }
        size++;

    }

    /**
     *
     * @param index the index of where to start upheap
     */
    private void upHeapHelper(int index) {
        while (index != 1 && backingArray[index].compareTo(backingArray[index / 2]) < 0) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;
            index /= 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The heap is empty");
        } else if (size == 1) {
            size--;
            T returnValue = backingArray[1];
            backingArray = (T[]) new Comparable[backingArray.length];
            return returnValue;
        } else {
            T returnValue = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            downHeapHelper(1);
            return returnValue;
        }
    }

    /**
     *
     * @param idx the index of the start of the downheap
     */
    private void downHeapHelper(int idx) {
        while ((idx < size) && (backingArray[idx * 2] != null || backingArray[(idx * 2) + 1] != null)) {
            T temp = backingArray[idx];
            if (backingArray[idx] == null) {
                break;
            } else if (backingArray[(idx * 2) + 1] == null && backingArray[idx * 2] != null) {
                if (backingArray[idx].compareTo(backingArray[(idx * 2)]) > 0) {
                    backingArray[idx] = backingArray[idx * 2];
                    backingArray[idx * 2] = temp;
                } else {
                    break;
                }
            } else if (backingArray[idx].compareTo(backingArray[(idx * 2)]) > 0 &&
                    backingArray[(idx * 2) + 1].compareTo(backingArray[(idx * 2)]) > 0) {
                backingArray[idx] = backingArray[idx * 2];
                backingArray[idx * 2] = temp;
                idx *= 2;
            } else if (backingArray[idx].compareTo(backingArray[(idx * 2) + 1]) > 0){
                backingArray[idx] = backingArray[(idx * 2) + 1];
                backingArray[(idx * 2) + 1] = temp;
                idx = ((idx * 2) + 1);
            } else {
                break;
            }
        }
    }
    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        T[] newArray = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray = newArray;
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
